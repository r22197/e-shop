package eshop.backend.exception;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}