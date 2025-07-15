package com.example.orderservice.connect.product.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {
    Long id;
    String productName;
    Double price;
}
