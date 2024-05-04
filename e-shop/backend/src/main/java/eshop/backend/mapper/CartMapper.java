package eshop.backend.mapper;

import eshop.backend.dto.CartDto;
import eshop.backend.dto.CartItemDto;
import eshop.backend.model.Cart;
import eshop.backend.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {
    public CartDto convertCartToDto(Cart cart, double totalPrice) {
        return new CartDto(
                cart.getId(),
                totalPrice,
                cart.getCartItems().stream()
                        .map(this::convertProductCartToDto)
                        .collect(Collectors.toList())
        );
    }

    public CartItemDto convertProductCartToDto(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getId(),
                cartItem.getQuantity(),
                cartItem.getCart().getId(),
                0,
                cartItem.getVariant().getProduct().getId()
        );
    }
}
