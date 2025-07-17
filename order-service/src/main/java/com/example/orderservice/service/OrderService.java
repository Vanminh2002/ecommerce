package com.example.orderservice.service;

import com.example.commonlib.dto.inventory.DecreaseStockRequest;
import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import com.example.orderservice.connect.Inventory.InventoryClient;
import com.example.orderservice.connect.product.ProductClient;
import com.example.orderservice.connect.user.UserClient;
import com.example.orderservice.dto.order.request.OrderCreateRequest;
import com.example.orderservice.dto.order.response.OrderResponse;
import com.example.orderservice.dto.orderItem.response.OrderItemResponse;
import com.example.orderservice.entities.Order;
import com.example.orderservice.entities.OrderItem;
import com.example.orderservice.event.OrderItemEvent;
import com.example.orderservice.event.OrderPlaceEvent;
import com.example.orderservice.mapper.OrderItemMapper;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.repository.OrderItemRepository;
import com.example.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    InventoryClient inventoryClient;
    OrderItemRepository orderItemRepository;
    OrderItemMapper orderItemMapper;
    RedisTemplate<String, String> redisStringTemplate;

    ObjectMapper objectMapper;

    @Transactional
    public OrderResponse create(OrderCreateRequest request) {

        try {
            for (var item : request.getOrderItems()) {
                if (item.getProductId() == null || item.getQuantity() == null || item.getQuantity() <= 0) {
                    throw new AppException(ErrorCode.INVALID_INPUT);
                }
                var product = productClient.getProductId(item.getProductId());
                if (product == null) {
                    throw new AppException(ErrorCode.NOT_FOUND);
                }
            }

            // gọi inventory
            List<DecreaseStockRequest.Item> items = request.getOrderItems().stream()
                    .map(item -> new DecreaseStockRequest.Item(item.getProductId(), item.getQuantity()))
                    .toList();

            DecreaseStockRequest decreaseStockRequest = new DecreaseStockRequest();
            decreaseStockRequest.setItems(items);
            // gọi hàm trừ kho

            try {
                inventoryClient.decreaseStock(decreaseStockRequest);
            } catch (FeignException.BadRequest e) {
                throw new AppException(ErrorCode.INSUFFICIENT_QUANTITY);
            }


            // lưu order và orderItem vào db
            Order order = orderMapper.toDto(request);
            // gán quan hệ cho từng orderItem

            var orderSave = orderRepository.save(order);

            List<OrderItem> orderItems = request.getOrderItems()
                    .stream()
                    .map(item -> {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrderId(orderSave.getId());
                        orderItem.setProductId(item.getProductId());
                        orderItem.setQuantity(item.getQuantity());
                        orderItem.setPrice(item.getPrice());
                        return orderItem;
                    }).toList();
            orderItemRepository.saveAll(orderItems);


            List<OrderItemEvent> events = orderItems.stream()
                    .map(i -> new OrderItemEvent(i.getProductId(), i.getQuantity(), i.getPrice()))
                    .toList();
            OrderPlaceEvent event = new OrderPlaceEvent(orderSave.getId(), orderSave.getUserId(), orderSave.getTotal(), events);

            kafkaTemplate.send("order-topic", event);
            log.warn("Đã gửi kafka event" + events);

            List<OrderItemResponse> itemResponses = orderItems.stream()
                    .map(i -> new OrderItemResponse()
                            .builder()
                            .productId(i.getProductId())
                            .quantity(i.getQuantity())
                            .price(i.getPrice())
                            .build())
                    .toList();

            return OrderResponse.builder()
                    .id(orderSave.getId())
                    .userId(orderSave.getUserId())
                    .orderItems(itemResponses)
                    .build();
        } catch (FeignException.BadRequest e) {
            throw new AppException(ErrorCode.INSUFFICIENT_QUANTITY);
        }
    }

    //    @Cacheable(value = "allOrders",key = "'all'")
//    public List<OrderResponse> findAll() {
//        try {
//            var orders = orderRepository.findAll();
//            log.warn("Lần đầu");
//            if (orders.isEmpty()) {
//                throw new RuntimeException("Không có order nào trong DB");
//            }
//            return orders.stream()
//                    .map(order -> {
//                        var response = orderMapper.toResponse(order);
//                        var item = orderItemRepository.findByOrderId(order.getId());
//                        var itemResponse = item.stream().map(orderItemMapper::orderItemToOrderItemResponse).toList();
//                        response.setOrderItems(itemResponse);
//                        return response;
//                    }).toList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Lỗi khi lấy tất cả order");
//        }
//    }
    public List<OrderResponse> findAll() {
        String cacheKey = "allOrders";
        String json = redisStringTemplate.opsForValue().get(cacheKey);
        if (json != null) {
            try {
                return objectMapper.readValue(json, new com.fasterxml.jackson.core.type.TypeReference<List<OrderResponse>>() {
                });
            } catch (Exception e) {
                // Nếu lỗi parse, xóa cache và lấy lại từ DB
                redisStringTemplate.delete(cacheKey);
            }
        }
        // Nếu cache miss, lấy từ DB và cache lại
        var orders = orderRepository.findAll();
        if (orders.isEmpty()) throw new RuntimeException("Không có order nào trong DB");
        List<OrderResponse> orderResponses = orders.stream()
                .map(order -> {
                    var response = orderMapper.toResponse(order);
                    var item = orderItemRepository.findByOrderId(order.getId());
                    var itemResponse = item.stream().map(orderItemMapper::orderItemToOrderItemResponse).toList();
                    response.setOrderItems(itemResponse);
                    return response;
                }).toList();
        try {
            String toCache = objectMapper.writeValueAsString(orderResponses);
            redisStringTemplate.opsForValue().set(cacheKey, toCache);
        } catch (Exception e) {
            // log lỗi serialize nếu có
        }
        return orderResponses;
    }

    public OrderResponse findById(Long id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("không có"));

        // Lấy danh sách order item theo orderId
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());


        // Map sang DTO response
        List<OrderItemResponse> itemResponses = orderItems.stream()
                .map(orderItemMapper::orderItemToOrderItemResponse)
                .toList();


        var user = userClient.getUserById(order.getUserId());
        var response = user.getData();


        List<OrderItemResponse> orderItemResponses = orderItems.stream()
                .map(item -> {
                    var productResponse = productClient.getProductId(item.getProductId());
                    var responseData = productResponse.getData();
                    return new OrderItemResponse().builder()
                            .productId(item.getProductId())
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .product(responseData)
                            .build();
                }).toList();


//        var product = productClient.getProductId(order.getProductId());
//        var productResponse = product.getData();
//
//        Double total = order.getQuantity() * productResponse.getPrice();
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .user(response)
                .orderItems(orderItemResponses)
                .build();
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


}
