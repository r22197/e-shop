package eshop.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class CartDto {
    private Long id;
    private double price;
    private List<CartItemDto> productsInCart;
}