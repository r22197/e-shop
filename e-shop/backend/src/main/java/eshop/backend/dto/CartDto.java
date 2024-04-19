package eshop.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
public class CartDto {
    private Long id;
    private Set<CartHasProductDto> productsInCart;
}