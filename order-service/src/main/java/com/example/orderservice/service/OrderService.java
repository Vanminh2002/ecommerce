package com.example.orderservice.service;

import com.example.orderservice.connect.product.ProductClient;
import com.example.orderservice.connect.user.UserClient;
import com.example.orderservice.dto.order.request.OrderCreateRequest;
import com.example.orderservice.dto.order.response.OrderResponse;
import com.example.orderservice.entities.Order;
import com.example.orderservice.event.OrderPlaceEvent;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    UserClient userClient;
    KafkaTemplate<String, OrderPlaceEvent> kafkaTemplate;
    ProductClient productClient;

    public OrderResponse create(OrderCreateRequest request) {
        try {
            Order order = orderMapper.toDto(request);
            var orderSave = orderRepository.save(order);

            OrderPlaceEvent event = OrderPlaceEvent.builder()
                    .orderId(orderSave.getId())
                    .userId(orderSave.getUserId())
                    .total(orderSave.getTotal())
                    .productId(orderSave.getProductId())
                    .build();
            kafkaTemplate.send("order-topic", event);
            log.warn("Đã gửi kafka event" + event);

            return orderMapper.toResponse(orderSave);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tạo order ");
        }
    }

    @Cacheable(value = "allOrders")
    public List<OrderResponse> findAll() {
        try {
            var orders = orderRepository.findAll();
            log.warn("Lần đầu");
            if (orders.isEmpty()) {
                throw new RuntimeException("Không có order nào trong DB");
            }
            return orders.stream().map(orderMapper::toResponse).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy tất cả order");
        }
    }


    public OrderResponse findById(Long id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("không có"));
        var user = userClient.getUserById(order.getUserId());
        var product = productClient.getProductId(order.getProductId());
        var productResponse = product.getData();
        var response = user.getData();
        Double total = order.getQuantity() * productResponse.getPrice();
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .productId(order.getProductId())
                .price(order.getPrice())
                .user(response)
                .product(productResponse)
                .totalPrice(total)
                .quantity(order.getQuantity())
                .build();
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
