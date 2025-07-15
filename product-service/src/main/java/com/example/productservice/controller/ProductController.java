package com.example.productservice.controller;

import com.example.commonlib.dto.PaginatedResponse;
import com.example.commonlib.dto.api.ApiResponse;
import com.example.productservice.dto.request.CreateProductRequest;
import com.example.productservice.dto.request.ProductSearchRequest;
import com.example.productservice.dto.request.ProductUpdateRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.entities.Product;
import com.example.productservice.service.ProductService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @PostMapping("create")
    ApiResponse<ProductResponse> create(@RequestBody CreateProductRequest request) {

        ProductResponse response = productService.createProduct(request);
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
    ApiResponse<ProductResponse> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        ProductResponse response = productService.UpdateProduct(id, request);
        return ApiResponse.<ProductResponse>builder()
                .data(response)
                .message("Cập nhật product thành công")
                .build();
    }

    @DeleteMapping("delete/{id}")
    ApiResponse<Void> delete(@PathVariable Long id) {
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
