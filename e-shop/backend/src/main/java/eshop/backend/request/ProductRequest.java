package eshop.backend.request;

public record ProductRequest(
        Long id,
        String name,
        String description,
        String imagePath,
        Long categoryId
) {}