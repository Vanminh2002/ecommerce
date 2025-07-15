package com.example.categoryservice.dto.category.request;

import com.example.commonlib.dto.BaseFilter;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class CategorySearchRequest extends BaseFilter {
    Long id;
    String categoryName;
    String description;
}
