package com.example.productservice.mapper;

import com.example.productservice.dto.request.CreateProductRequest;
import com.example.productservice.dto.request.ProductUpdateRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.entities.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toDto(CreateProductRequest request);

    @Mapping(target = "images", expression = "java(parseImages(product.getImages()))")
    ProductResponse toResponse(Product product);
//    @Mapping(target = "image",source = "image")
    public void updateProduct(@MappingTarget Product product, ProductUpdateRequest request);

    default List<String> parseImages(String imagesJson) {
        if (imagesJson == null || imagesJson.isEmpty()) return new ArrayList<>();
        try {
            return new ObjectMapper().readValue(imagesJson, new TypeReference<List<String>>() {
            });
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }
}
