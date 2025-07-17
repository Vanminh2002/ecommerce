package com.example.orderservice.dto.orderItem.response;

import com.example.orderservice.connect.product.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemResponse  {
    Long productId;
    Integer quantity;
    Double price;
    ProductDto product;
}
