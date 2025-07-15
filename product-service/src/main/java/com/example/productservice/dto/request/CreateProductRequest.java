package com.example.productservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProductRequest {
    String productName;

    String description;

    Double price;

    Integer quantity;

    Integer inventory;

    String image;

    Long categoryId;
}
