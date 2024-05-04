package eshop.backend.request;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductRequest {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Long category;
    private String imagePath;
}