package com.example.productservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "product_name")
    String productName;

    @Column(name = "sku")
    String sku; // Mã hàng hóa (Stock Keeping Unit) - dùng cho quản lý kho

    @Column(name = "brand")
    String brand; //Thương hiệu sản phẩm

    @Column(name = "description")
    String description;

    @Column(name = "price")
    Double price;

    @Column(name = "price_original")
    Double priceOriginal; // Giá gốc (trước khi giảm giá)

    @Column(name = "discount")
    Double discount; // Phần trăm hoặc số tiền giảm giá

    @Column(name = "images",length = 4000)
    String images; // Danh sách nhiều ảnh (nên có bảng riêng hoặc lưu JSON)

    @Column(name = "image",length = 4000)
    String image;

    @Column(name = "origin")
    String origin; // Xuất xứ

    @Column(name = "dimensions")
    String dimensions; // Kích thước

    @Column(name = "tags")
    String tags; // Từ khóa/tags sản phẩm

    @Column(name = "category_id")
    Long categoryId;

    @Column(name = "created_at")
    LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "created_by")
    String createdBy;

    @Column(name = "updated_by")
    String updatedBy;


    public Double getFinalPrice() {
        if (discount != null && discount > 0) {
            // Giả sử discount là phần trăm
            return priceOriginal * (1 - discount / 100);
        }
        return priceOriginal;
    }
}
