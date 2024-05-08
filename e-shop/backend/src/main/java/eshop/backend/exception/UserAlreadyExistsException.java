package eshop.backend.exception;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String exception) {
        super(exception);
    }
}