package eshop.backend.response;


import eshop.backend.model.CartHasProduct;
import eshop.backend.model.Product;
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

    public void convertFromProduct(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory().getId();
    }
}