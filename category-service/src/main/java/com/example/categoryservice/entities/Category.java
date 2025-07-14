package com.example.categoryservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "category_name")
    String categoryName;
    @Column(name = "description")
    String description;
    @Column(name = "create_at")
    LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "update_at")
    LocalDateTime updatedAt = LocalDateTime.now();

}
