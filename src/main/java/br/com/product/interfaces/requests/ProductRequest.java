package br.com.product.interfaces.requests;

import io.micronaut.core.annotation.Introspected;
import lombok.*;


@Builder
@Data
@Introspected
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private String category;
    private Double price;
}
