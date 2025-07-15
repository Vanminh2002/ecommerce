package com.example.productservice.dto.response;

import com.example.productservice.connect.category.dto.CategoryDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    Long id;
    String productName;

    String description;

    Double price;

    Integer quantity;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    Integer inventory;

    String image;

    Long categoryId;

    CategoryDto category;
}
