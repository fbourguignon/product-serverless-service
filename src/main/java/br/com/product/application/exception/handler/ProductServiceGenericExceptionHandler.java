package br.com.product.application.exception.handler;

import br.com.product.application.exception.ProductServiceGenericException;
import br.com.product.interfaces.responses.ExceptionResponse;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;
import java.util.logging.Handler;


@Produces
@Singleton
@Requires(classes = {ProductServiceGenericException.class, Handler.class})
public class ProductServiceGenericExceptionHandler implements ExceptionHandler<ProductServiceGenericException, ExceptionResponse> {
    public ExceptionResponse handle(HttpRequest request, ProductServiceGenericException exception) {
        return ExceptionResponse
                .builder()
                .message(exception.getMessage())
                .build();
    }
}