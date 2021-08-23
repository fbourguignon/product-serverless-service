package br.com.product.application.mapper;

import br.com.product.domain.entity.Product;
import br.com.product.interfaces.requests.ProductRequest;
import br.com.product.interfaces.responses.ProductResponse;

import javax.inject.Singleton;

@Singleton
public class ProductMapper {

    public ProductResponse toResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId().toString())
                .name(product.getName())
                .category(product.getCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public Product toEntity(ProductRequest request){
        return Product.builder()
                .name(request.getName())
                .category(request.getCategory())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }
}
