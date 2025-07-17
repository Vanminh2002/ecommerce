package com.example.inventoryservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "product_id")
    Long productId;   // Liên kết với Product

    @Column(name = "quantity")
    Integer quantity;  // Số lượng tồn kho hiện tại

    @Column(name = "reserved")
    Integer reserved; // Số lượng đã đặt nhưng chưa giao

    @Column(name = "sold")
    Integer sold;  // Số lượng đã bán

    @Column(name = "warehouse")
    String warehouse;  // Tên hoặc mã kho

    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}
