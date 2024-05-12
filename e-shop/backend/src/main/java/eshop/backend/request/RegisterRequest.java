package eshop.backend.request;

public record RegisterRequest(
        String email,
        String password,
        String firstName,
        String lastName
) {}
