package com.example.categoryservice.controller;


import com.example.categoryservice.dto.api.CategoryApiResponse;
import com.example.categoryservice.dto.category.request.CategorySearchRequest;
import com.example.categoryservice.dto.category.request.CreateCategoryRequest;
import com.example.categoryservice.dto.category.response.CategoryResponse;

import com.example.categoryservice.services.CategoryService;
import com.example.commonlib.dto.PaginatedResponse;
import com.example.commonlib.dto.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @PostMapping("create")
    CategoryApiResponse<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return CategoryApiResponse.<CategoryResponse>builder()
                .data(response)
                .message("Tạo mới danh mục thành công")
                .build();
    }

    @GetMapping("get-all")
    CategoryApiResponse<PaginatedResponse<CategoryResponse>> getAllCategories(
            @Valid @ModelAttribute CategorySearchRequest request) {

        PaginatedResponse<CategoryResponse> response = categoryService.getAllCategories(request);
        return CategoryApiResponse.<PaginatedResponse<CategoryResponse>>builder()
                .data(response)
                .message("Danh sách danh mục")
                .build();
    }

    @PutMapping("update/{id}")
    CategoryApiResponse<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CreateCategoryRequest request) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        return CategoryApiResponse.<CategoryResponse>builder()
                .data(response)
                .message("Cập nhật danh mục thành công")
                .build();
    }

    @DeleteMapping("delete/{id}")
    CategoryApiResponse<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return CategoryApiResponse.<Void>builder()
                .message("Xóa danh mục thành công")
                .build();
    }

    @GetMapping("get-by/{id}")
    ApiResponse<CategoryResponse> getById(@PathVariable Long id) {
        CategoryResponse response = categoryService.getByCategoryId(id);
        return ApiResponse.<CategoryResponse>builder()
                .data(response)
                .message("Danh mục có id = " + id)
                .build();
    }
}

