package com.example.productservice.mapper;

import com.example.productservice.dto.request.CreateProductRequest;
import com.example.productservice.dto.request.ProductUpdateRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.entities.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-15T16:38:18+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.7.jar, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toDto(CreateProductRequest request) {
        if ( request == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.productName( request.getProductName() );
        product.description( request.getDescription() );
        product.price( request.getPrice() );
        product.quantity( request.getQuantity() );
        product.inventory( request.getInventory() );
        product.image( request.getImage() );
        product.categoryId( request.getCategoryId() );

        return product.build();
    }

    @Override
    public ProductResponse toResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.id( product.getId() );
        productResponse.productName( product.getProductName() );
        productResponse.description( product.getDescription() );
        productResponse.price( product.getPrice() );
        productResponse.quantity( product.getQuantity() );
        productResponse.createdAt( product.getCreatedAt() );
        productResponse.updatedAt( product.getUpdatedAt() );
        productResponse.inventory( product.getInventory() );
        productResponse.image( product.getImage() );
        productResponse.categoryId( product.getCategoryId() );

        return productResponse.build();
    }

    @Override
    public void updateProduct(Product product, ProductUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        product.setPrice( request.getPrice() );
        product.setQuantity( request.getQuantity() );
        product.setInventory( request.getInventory() );
        product.setImage( request.getImage() );
    }
}
