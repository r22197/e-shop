package eshop.backend.service;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.CartHasProduct;
import eshop.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CartService {
    void addProduct(Long ProductId);
    void removeProduct(Long ProductId);
    List<CartHasProduct> getCart();

    CartHasProduct updateProductCartQuantity(Long id, Integer quantity);
}
