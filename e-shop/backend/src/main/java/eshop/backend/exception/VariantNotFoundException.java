package eshop.backend.exception;

public class VariantNotFoundException extends RuntimeException {
    public VariantNotFoundException(Long attributeId) {
        super(String.format("Attribute with ID %d was not found.", attributeId));
    }
}
