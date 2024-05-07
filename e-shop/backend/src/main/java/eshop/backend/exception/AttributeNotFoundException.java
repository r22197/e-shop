package eshop.backend.exception;

public class AttributeNotFoundException extends RuntimeException {
    public AttributeNotFoundException(Long attributeId) {
        super(String.format("Attribute with ID %d was not found.", attributeId));
    }
}
