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
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long productId;       // Liên kết với Product
    Integer quantity;     // Số lượng tồn kho hiện tại
    Integer reserved;     // Số lượng đã đặt nhưng chưa giao
    Integer sold;         // Số lượng đã bán
    String warehouse;     // Tên hoặc mã kho
    LocalDateTime updatedAt;
}
