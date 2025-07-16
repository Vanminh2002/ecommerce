package com.example.productservice.connect.category;

import com.example.commonlib.dto.api.ApiResponse;
import com.example.productservice.connect.category.dto.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service")
public interface CategoryClient {
    @GetMapping("/categories/get-by/{id}")
    ApiResponse<CategoryDto> getByCategoryId(@PathVariable("id") Long id);
}
