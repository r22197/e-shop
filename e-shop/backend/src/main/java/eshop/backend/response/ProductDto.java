package eshop.backend.response;

import eshop.backend.model.CartHasProduct;
import lombok.*;

import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Long category;
    private Set<CartHasProduct> productInCarts;

    public ProductDto(String name, String description, double price, Long category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}