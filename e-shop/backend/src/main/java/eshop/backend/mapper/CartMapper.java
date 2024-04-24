package eshop.backend.mapper;

import eshop.backend.dto.CartDto;
import eshop.backend.dto.CartHasProductDto;
import eshop.backend.model.Cart;
import eshop.backend.model.CartHasProduct;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {
    public CartDto convertCartToDto(Cart cart, double totalPrice) {
        return new CartDto(
                cart.getId(),
                totalPrice,
                cart.getProductsInCart().stream()
                        .map(this::convertProductCartToDto)
                        .collect(Collectors.toList())
        );
    }

    public CartHasProductDto convertProductCartToDto(CartHasProduct cartHasProduct) {
        return new CartHasProductDto(
                cartHasProduct.getId(),
                cartHasProduct.getQuantity(),
                cartHasProduct.getCart().getId(),
                cartHasProduct.getProduct().getId()
        );
    }
}
