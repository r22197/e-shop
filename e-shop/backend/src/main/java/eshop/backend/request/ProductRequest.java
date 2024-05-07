package eshop.backend.request;

import lombok.Data;

@Data
public class ProductRequest {
    private Long id;
    private String name;
    private String description;
    private String imagePath;
    private Long categoryId;
}