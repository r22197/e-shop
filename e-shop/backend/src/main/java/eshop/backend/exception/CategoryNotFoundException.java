package eshop.backend.exception;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException(String err) {
        super(err);
    }
}
