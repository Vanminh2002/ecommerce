package com.example.productservice.mapper;

import com.example.productservice.dto.request.CreateProductRequest;
import com.example.productservice.dto.request.ProductUpdateRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toDto(CreateProductRequest request);

    ProductResponse toResponse(Product product);

    public void updateProduct(@MappingTarget Product product, ProductUpdateRequest request);
}
