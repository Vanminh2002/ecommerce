package com.example.categoryservice.controller;

import com.example.categoryservice.dto.api.ApiResponse;
import com.example.categoryservice.dto.category.request.CategorySearchRequest;
import com.example.categoryservice.dto.category.request.CreateCategoryRequest;
import com.example.categoryservice.dto.category.response.CategoryResponse;
import com.example.categoryservice.dto.category.response.PaginatedResponse;
import com.example.categoryservice.services.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
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
    ApiResponse<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return ApiResponse.<CategoryResponse>builder()
                .data(response)
                .message("Tạo mới danh mục thành công")
                .build();
    }

    @GetMapping("get-all")
    ApiResponse<PaginatedResponse<CategoryResponse>> getAllCategories(
            @Valid @ModelAttribute CategorySearchRequest request) {

        PaginatedResponse<CategoryResponse> response = categoryService.getAllCategories(request);
        return ApiResponse.<PaginatedResponse<CategoryResponse>>builder()
                .data(response)
                .message("Danh sách danh mục")
                .build();
    }

    @PutMapping("update/{id}")
    ApiResponse<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CreateCategoryRequest request) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        return ApiResponse.<CategoryResponse>builder()
                .data(response)
                .message("Cập nhật danh mục thành công")
                .build();
    }

    @DeleteMapping("delete/{id}")
    ApiResponse<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<Void>builder()
                .message("Xóa danh mục thành công")
                .build();
    }

//    @PostMapping("/categories/search")
//    public ApiResponse<List<CategoryResponse>> getCategories(@RequestBody CategorySearchRequest request) {
//        List<CategoryResponse> responses = categoryService.getAllCategories(request);
//        return ApiResponse.<List<CategoryResponse>>builder()
//                .data(responses)
//                .message("Tìm kiếm thành công")
//                .build();
//    }
}

