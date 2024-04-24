package eshop.backend.service;

import eshop.backend.model.Cart;
import eshop.backend.model.CartHasProduct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CartService {
    void addProduct(Cart cart, Long productId);
    void removeProduct(Cart cart, Long ProductId) throws IllegalAccessException;
    Cart getCartByUserEmail(String email);
    void updateProductCartQuantity(Cart cart, Long id, Integer quantity) throws IllegalAccessException;

    double calculateTotalPrice(Cart cart);
}
