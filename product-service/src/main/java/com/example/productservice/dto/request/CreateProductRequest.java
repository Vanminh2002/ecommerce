package com.example.productservice.dto.request;

import jakarta.persistence.Column;
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

    String sku; // Mã hàng hóa (Stock Keeping Unit) - dùng cho quản lý kho

    String brand; //Thương hiệu sản phẩm

    String description;

    Double price;

    Double priceOriginal; // Giá gốc (trước khi giảm giá)

    Double discount; // Phần trăm hoặc số tiền giảm giá

//    String images; // Danh sách nhiều ảnh (nên có bảng riêng hoặc lưu JSON)

//    String image;

    String origin; // Xuất xứ

    String dimensions; // Kích thước

    String tags; // Từ khóa/tags sản phẩm

    Long categoryId;

    String createdBy;

    String updatedBy;
}
