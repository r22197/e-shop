package eshop.backend.exception;

public class PriceNotFoundException extends Exception {
    public PriceNotFoundException(Long attributeId) {
        super(String.format("Attribute with ID %d was not found.", attributeId));
    }
}
