package eshop.backend.service;

import eshop.backend.model.CartHasProduct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CartService {
    void addProduct(Long ProductId);
    void removeProduct(Long ProductId);
    List<CartHasProduct> getCart();

    void updateProductCartQuantity(Long id, Integer quantity);

    double calculateTotalPrice();
}
