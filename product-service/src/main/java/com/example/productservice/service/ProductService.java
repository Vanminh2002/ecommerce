package com.example.productservice.service;

import com.example.commonlib.dto.PaginatedResponse;
import com.example.commonlib.dto.api.ApiResponse;
import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import com.example.productservice.connect.category.CategoryClient;
import com.example.productservice.connect.file.FileClient;
import com.example.productservice.dto.request.CreateProductRequest;
import com.example.productservice.dto.request.ProductSearchRequest;
import com.example.productservice.dto.request.ProductUpdateRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.entities.Product;
import com.example.productservice.mapper.ProductMapper;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.repository.ProductRepositoryCustom;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.BadRequestException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    ProductRepositoryCustom productRepositoryCustom;
    CategoryClient categoryClient;
    FileClient fileClient;

    public ProductResponse createProduct(CreateProductRequest request, List<MultipartFile> files) {
        try {
            if (productRepository.existsByProductName(request.getProductName())) {
                throw new AppException(ErrorCode.EXISTED);
            }

            List<String> urlImages = new ArrayList<>();
            for (MultipartFile file : files) {
                ApiResponse<String> uploadResponse = fileClient.uploadImage(file);
                urlImages.add(uploadResponse.getData());
            }

            String imageUrl = urlImages.isEmpty() ? null : urlImages.get(0);

            String imageJson = new ObjectMapper().writeValueAsString(urlImages);
            Product product = productMapper.toDto(request);
            product.setImage(imageUrl);
            product.setImages(imageJson);
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());
            product.setPrice(product.getFinalPrice());
            productRepository.save(product);
            return productMapper.toResponse(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.ERROR_SERVER);
        }
    }

    public PaginatedResponse<ProductResponse> getAllProduct(ProductSearchRequest request) {
        if (request.getId() != null && !productRepository.findById(request.getId()).isPresent()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        Page<Product> productPage = productRepositoryCustom.findProducts(request);
        List<ProductResponse> data = productPage
                .getContent()
                .stream()
                .map(productMapper::toResponse).
                toList();
        return new PaginatedResponse<>(
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                data
        );
    }

    public ProductResponse UpdateProduct(Long id, ProductUpdateRequest request, List<MultipartFile> files) throws JsonProcessingException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        if (files != null && !files.isEmpty()) {
            deleteImageProduct(product);
            List<String> urlImages = new ArrayList<>();
            for (MultipartFile file : files) {
                ApiResponse<String> uploadResponse = fileClient.uploadImage(file);
                urlImages.add(uploadResponse.getData());
            }
            String imageUrl = urlImages.isEmpty() ? null : urlImages.get(0);
            String imageJson = new ObjectMapper().writeValueAsString(urlImages);
            product.setImage(imageUrl);
            product.setImages(imageJson);
        }

        productMapper.updateProduct(product, request);


        var save = productRepository.save(product);
        return productMapper.toResponse(save);
    }

    public void deleteProduct(Long id) throws JsonProcessingException {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        deleteImageProduct(product);
        productRepository.deleteById(id);
    }


    public ProductResponse getProductById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        var category = categoryClient.getByCategoryId(product.getCategoryId());
        if (category == null || category.getData() == null) {
            throw new BadRequestException("Danh mục không tồn tại");
        }
        var data = category.getData();

        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .description(product.getDescription())
                .image(product.getImage())
                .images(Collections.singletonList(product.getImages()))
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .categoryId(product.getCategoryId())
                .category(data)
                .build();
    }


    private void deleteImageProduct(Product product) throws JsonProcessingException {



        if (product.getImages() != null && !product.getImages().isBlank()) {
            List<String> images = new ObjectMapper().readValue(product.getImages(), new TypeReference<List<String>>() {});
            for (String imageUrl : images) {
                fileClient.deleteImage(imageUrl);
            }
        } else if (product.getImage() != null && !product.getImage().isBlank()) {
            // Nếu chỉ có 1 ảnh
            fileClient.deleteImage(product.getImage());
        }

//
//        if (product.getImage() != null && !product.getImage().isBlank()) {
//            fileClient.deleteImage(product.getImage());
//        }
//        if (product.getImages() != null) {
//            List<String> images = new ObjectMapper().readValue(product.getImages(), new TypeReference<List<String>>() {
//            });
//            for (String imageUrl : images) {
//                fileClient.deleteImage(imageUrl);
//            }
//        }
    }
}
