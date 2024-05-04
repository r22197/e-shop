package eshop.backend.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Long attributeId) {
        super(String.format("Attribute with ID %d was not found.", attributeId));
    }
}
