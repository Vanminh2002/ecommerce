package com.example.orderservice.dto.order.response;


import com.example.orderservice.connect.user.dto.UserDto;
import com.example.orderservice.dto.orderItem.response.OrderItemResponse;
import com.example.orderservice.entities.OrderItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    Long id;
    Long userId;
    UserDto user;
    List<OrderItemResponse> orderItems;
}
