package com.example.categoryservice.dto.category.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    Long id;
    String categoryName;
    String description;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
