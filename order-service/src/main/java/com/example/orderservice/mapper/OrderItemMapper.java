package com.example.orderservice.mapper;

import com.example.orderservice.dto.orderItem.response.OrderItemResponse;
import com.example.orderservice.entities.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemResponse orderItemToOrderItemResponse(OrderItem orderItem);
}
