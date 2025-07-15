package com.example.orderservice.connect.product;

import com.example.orderservice.connect.product.dto.ProductDto;
import com.example.orderservice.connect.user.dto.UserDto;
import com.example.orderservice.dto.api.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/product/get-by/{id}")
    ApiResponse<ProductDto> getProductId(@PathVariable("id") Long id);
}
