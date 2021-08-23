package br.com.product.domain.entity;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class Product {
    private ObjectId id;
    private String name;
    private String description;
    private String category;
    private Double price;
    private LocalDate createDate;
}
