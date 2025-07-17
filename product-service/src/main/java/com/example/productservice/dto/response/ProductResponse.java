package com.example.productservice.dto.response;

import com.example.productservice.connect.category.dto.CategoryDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    Long id;

    String productName;

    String sku; // Mã hàng hóa (Stock Keeping Unit) - dùng cho quản lý kho

    String brand; //Thương hiệu sản phẩm

    String description;

    Double price;

    Double priceOriginal; // Giá gốc (trước khi giảm giá)

    Double discount; // Phần trăm hoặc số tiền giảm giá

    List<String> images; // Danh sách nhiều ảnh (nên có bảng riêng hoặc lưu JSON)

    String image;

    String origin; // Xuất xứ

    String dimensions; // Kích thước

    String tags; // Từ khóa/tags sản phẩm

    LocalDateTime createdAt = LocalDateTime.now();

    LocalDateTime updatedAt = LocalDateTime.now();

    String createdBy;

    String updatedBy;

    Long categoryId;

    CategoryDto category;
}
