package com.example.productservice.mapper;

import com.example.productservice.dto.request.CreateProductRequest;
import com.example.productservice.dto.request.ProductUpdateRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.entities.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-16T14:57:30+0700",
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
        product.sku( request.getSku() );
        product.brand( request.getBrand() );
        product.description( request.getDescription() );
        product.price( request.getPrice() );
        product.priceOriginal( request.getPriceOriginal() );
        product.discount( request.getDiscount() );
        product.images( request.getImages() );
        product.origin( request.getOrigin() );
        product.dimensions( request.getDimensions() );
        product.tags( request.getTags() );
        product.categoryId( request.getCategoryId() );
        product.createdBy( request.getCreatedBy() );
        product.updatedBy( request.getUpdatedBy() );

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
        productResponse.sku( product.getSku() );
        productResponse.brand( product.getBrand() );
        productResponse.description( product.getDescription() );
        productResponse.price( product.getPrice() );
        productResponse.priceOriginal( product.getPriceOriginal() );
        productResponse.discount( product.getDiscount() );
        productResponse.image( product.getImage() );
        productResponse.origin( product.getOrigin() );
        productResponse.dimensions( product.getDimensions() );
        productResponse.tags( product.getTags() );
        productResponse.createdAt( product.getCreatedAt() );
        productResponse.updatedAt( product.getUpdatedAt() );
        productResponse.createdBy( product.getCreatedBy() );
        productResponse.updatedBy( product.getUpdatedBy() );
        productResponse.categoryId( product.getCategoryId() );

        productResponse.images( parseImages(product.getImages()) );

        return productResponse.build();
    }

    @Override
    public void updateProduct(Product product, ProductUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        product.setPrice( request.getPrice() );
        product.setImage( request.getImage() );
    }
}
