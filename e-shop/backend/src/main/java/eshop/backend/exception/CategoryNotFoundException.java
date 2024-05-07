package eshop.backend.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long attributeId) {
        super(String.format("Attribute with ID %d was not found.", attributeId));
    }
}
