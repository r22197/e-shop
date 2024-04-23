package eshop.backend.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Long category;
}