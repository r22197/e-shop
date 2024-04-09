package eshop.backend.service;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Cart;
import eshop.backend.model.User;
import eshop.backend.request.AddItemRequest;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    public Cart createCart(User user);
    public String addProductToCart(Long userId, AddItemRequest addItemRequest) throws ProductNotFoundException;
    Cart findUserCartById(Long userId);
}
