package eshop.backend.service;

import eshop.backend.model.Cart;
import eshop.backend.model.CartHasProduct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CartService {
    void addProduct(Cart cart, Long productId);
    void removeProduct(Long ProductId);
    Cart getCartByUserEmail(String email);

    void updateProductCartQuantity(Long id, Integer quantity);

    double calculateTotalPrice();
}
