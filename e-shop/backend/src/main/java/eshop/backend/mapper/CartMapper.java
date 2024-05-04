package eshop.backend.mapper;

import eshop.backend.request.CartRequest;
import eshop.backend.request.CartItemRequest;
import eshop.backend.model.Cart;
import eshop.backend.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {
    public CartRequest convertCartToDto(Cart cart, double totalPrice) {
        return new CartRequest(
                cart.getId(),
                totalPrice,
                cart.getCartItems().stream()
                        .map(this::convertProductCartToDto)
                        .collect(Collectors.toList())
        );
    }

    public CartItemRequest convertProductCartToDto(CartItem cartItem) {
        return new CartItemRequest(
                cartItem.getId(),
                cartItem.getQuantity(),
                cartItem.getCart().getId(),
                0,
                cartItem.getVariant().getProduct().getId()
        );
    }
}
