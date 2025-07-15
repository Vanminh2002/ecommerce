package com.example.orderservice.controller;

import com.example.orderservice.connect.user.UserClient;
import com.example.orderservice.dto.api.ApiResponse;
import com.example.orderservice.dto.order.request.OrderCreateRequest;
import com.example.orderservice.dto.order.response.OrderResponse;
import com.example.orderservice.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;


    @PostMapping("create")
    @CacheEvict(value = "allOrders", allEntries = true)
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderCreateRequest request) {
        OrderResponse response = orderService.create(request);
        return ApiResponse.<OrderResponse>builder()
                .message("Order đã được tạo")
                .data(response)
                .build();
    }


    @GetMapping("get-all")
    ApiResponse<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> responses = orderService.findAll();
        return ApiResponse.<List<OrderResponse>>builder()
                .data(responses)
                .message("lấy thông tin tất cả order thành công")
                .build();
    }

    @GetMapping("get-by/{id}")
    ApiResponse<OrderResponse> getOrderById(@PathVariable Long id) {
        OrderResponse response = orderService.findById(id);
        return ApiResponse.<OrderResponse>builder()
                .data(response)
                .message("Thông tin đơn hàng")
                .build();
    }

    @DeleteMapping("delete/{id}")
    ApiResponse<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ApiResponse.<Void>builder()
                .message("Xóa đơn hàng thành công")
                .build();
    }
}
