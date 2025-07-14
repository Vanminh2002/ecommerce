package com.example.categoryservice.mapper;

import com.example.categoryservice.dto.category.request.CreateCategoryRequest;
import com.example.categoryservice.dto.category.response.CategoryResponse;
import com.example.categoryservice.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toDto(CreateCategoryRequest request);

    CategoryResponse toResponse(Category category);

    public void updateCategory(@MappingTarget Category category, CreateCategoryRequest request);

}
