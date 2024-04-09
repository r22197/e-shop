package eshop.backend.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}