package br.com.product.interfaces.responses;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Introspected
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
}
