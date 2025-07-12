package com.example.orderservice.dto.order.response;

import com.example.orderservice.connect.user.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    Long id;
    Long userId;
    String product;
    double price;
    UserDto user;
}
