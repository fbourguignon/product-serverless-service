package br.com.product.application.exception;

public class ProductServiceGenericException extends RuntimeException{
    public ProductServiceGenericException(String message) {
        super(message);
    }
}
