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

    @Column(name = "description")
    String description;

    @Column(name = "price")
    Double price;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "created_at")
    LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "inventory")
    Integer inventory;

    @Column(name = "image")
    String image;

    @Column(name = "category_id")
    Long categoryId;

}
