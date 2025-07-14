package com.example.categoryservice.services;


import com.example.categoryservice.dto.category.request.CategorySearchRequest;
import com.example.categoryservice.dto.category.request.CreateCategoryRequest;
import com.example.categoryservice.dto.category.response.CategoryResponse;
import com.example.categoryservice.dto.category.response.PaginatedResponse;
import com.example.categoryservice.entities.Category;
import com.example.categoryservice.mapper.CategoryMapper;
import com.example.categoryservice.repository.CategoryRepository;
import com.example.categoryservice.repository.CategoryRepositoryCustom;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    CategoryRepositoryCustom categoryRepositoryCustom;


    public CategoryResponse createCategory(CreateCategoryRequest request) {
        try {
            if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
                throw new RuntimeException("Danh mục đã tồn tại");
            }
            Category category = categoryMapper.toDto(request);
            category.setCreatedAt(LocalDateTime.now());
            category.setUpdatedAt(LocalDateTime.now());
            category = categoryRepository.save(category);
            return categoryMapper.toResponse(category);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("Lỗi khi tạo danh mục");
        }
    }

    public PaginatedResponse<CategoryResponse> getAllCategories(CategorySearchRequest request) {
        try {

            Page<Category> categories = categoryRepositoryCustom.categoryPage(request);
            List<CategoryResponse> data = categories.stream().map(categoryMapper::toResponse).toList();
//            log.warn("Lần đầu");
//            if (categories.isEmpty()) {
//                throw new RuntimeException("Không có danh mục nào trong DB");
//            }
            return new PaginatedResponse<>(
                    categories.getNumber(),
                    categories.getSize(),
                    categories.getTotalElements(),
                    categories.getTotalPages(),
                    data
            );
        } catch (Exception e) {
            log.error("Lỗi", e);
            throw new RuntimeException("Lỗi khi hiển thị danh mục");
        }
    }

    public CategoryResponse updateCategory(Long id, CreateCategoryRequest request) {
        try {

            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại "));
            categoryMapper.updateCategory(category, request);
            var save = categoryRepository.save(category);
            return categoryMapper.toResponse(save);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("Lỗi khi cập nhật danh mục");
        }
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
