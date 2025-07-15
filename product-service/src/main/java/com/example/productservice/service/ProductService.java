package com.example.productservice.service;

import com.example.commonlib.dto.PaginatedResponse;
import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import com.example.productservice.connect.category.CategoryClient;
import com.example.productservice.dto.request.CreateProductRequest;
import com.example.productservice.dto.request.ProductSearchRequest;
import com.example.productservice.dto.request.ProductUpdateRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.entities.Product;
import com.example.productservice.mapper.ProductMapper;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.repository.ProductRepositoryCustom;
import jakarta.ws.rs.BadRequestException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public ProductResponse createProduct(CreateProductRequest request) {
        try {
            if (productRepository.existsByProductName(request.getProductName())) {
                throw new AppException(ErrorCode.EXISTED);
            }
            Product product = productMapper.toDto(request);
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);
            return productMapper.toResponse(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.ERROR_SERVER);
        }
    }

    public PaginatedResponse<ProductResponse> getAllProduct(ProductSearchRequest request) {
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

    public ProductResponse UpdateProduct(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        productMapper.updateProduct(product, request);
        var save = productRepository.save(product);
        return productMapper.toResponse(save);
    }

    public void deleteProduct(Long id) {
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
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .inventory(product.getInventory())
                .quantity(product.getQuantity())
                .categoryId(product.getCategoryId())
                .category(data)
                .build();
    }
}
