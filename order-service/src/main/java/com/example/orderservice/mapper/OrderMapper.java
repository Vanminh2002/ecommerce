package com.example.orderservice.mapper;

import com.example.orderservice.dto.order.request.OrderCreateRequest;
import com.example.orderservice.dto.order.response.OrderResponse;
import com.example.orderservice.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "orderItems", source = "orderItems")
    Order toDto(OrderCreateRequest request);

    OrderResponse toResponse(Order order);
}
