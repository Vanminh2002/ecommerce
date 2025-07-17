package com.example.productservice.controller;

import com.example.commonlib.dto.PaginatedResponse;
import com.example.commonlib.dto.api.ApiResponse;
import com.example.productservice.dto.request.CreateProductRequest;
import com.example.productservice.dto.request.ProductSearchRequest;
import com.example.productservice.dto.request.ProductUpdateRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.entities.Product;
import com.example.productservice.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @PostMapping("create")
    ApiResponse<ProductResponse> create(@ModelAttribute CreateProductRequest request, @RequestParam List<MultipartFile> file) {

        ProductResponse response = productService.createProduct(request, file);
        return ApiResponse.<ProductResponse>builder()
                .message("Tạo mới sản phẩm thành công")
                .data(response).build();
    }


    @GetMapping("get-all")
    ApiResponse<PaginatedResponse<ProductResponse>> getAll(@ModelAttribute ProductSearchRequest request) {
        PaginatedResponse<ProductResponse> response = productService.getAllProduct(request);
        return ApiResponse.<PaginatedResponse<ProductResponse>>builder()
                .data(response)
                .message("Danh sách sản phẩm")
                .build();
    }

    @PutMapping("update/{id}")
    ApiResponse<ProductResponse> update(@PathVariable Long id, @ModelAttribute ProductUpdateRequest request, List<MultipartFile> file) throws JsonProcessingException {
        ProductResponse response = productService.UpdateProduct(id, request, file);
        return ApiResponse.<ProductResponse>builder()
                .data(response)
                .message("Cập nhật product thành công")
                .build();
    }

    @DeleteMapping("delete/{id}")
    ApiResponse<Void> delete(@PathVariable Long id) throws JsonProcessingException {
        productService.deleteProduct(id);
        return ApiResponse.<Void>builder()
                .message("Xóa sản phẩm thành công")
                .build();
    }

    @GetMapping("get-by/{id}")
    ApiResponse<ProductResponse> getById(@PathVariable Long id) {
        ProductResponse response = productService.getProductById(id);
        return ApiResponse.<ProductResponse>builder()
                .data(response)
                .message("Sản phẩm có id = " + id)
                .build();
    }



}
