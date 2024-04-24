package eshop.backend.service.impl;

import eshop.backend.model.Cart;
import eshop.backend.model.CartHasProduct;
import eshop.backend.model.Product;
import eshop.backend.repository.CartHasProductRepository;
import eshop.backend.repository.CartRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ProductRepository productRepository;
    private final CartHasProductRepository cartHasProductRepository;
    private final CartRepository cartRepository;

    @Override
    public Cart getCartByUserEmail(String email) {

        return cartRepository.findCartByUserEmail(email);
    }

    @Override
    public void addProduct(Cart cart, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        Optional<CartHasProduct> existingCartItem = cartHasProductRepository.findByCartAndProduct(cart, product);

        if (existingCartItem.isPresent()) {
            CartHasProduct cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartHasProductRepository.save(cartItem);
        } else {
            CartHasProduct newCartItem = new CartHasProduct();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(1);
            newCartItem.setCart(cart);
            cartHasProductRepository.save(newCartItem);
        }
    }

    @Override
    public void updateProductCartQuantity(Cart cart, Long id, Integer quantity) throws IllegalAccessException {
        CartHasProduct existingCartItem = cartHasProductRepository.findById(id)
                .orElseThrow();

        if (existingCartItem.getCart() != cart) {
            throw new IllegalAccessException("The quantity can be only changed by cart's owner.");
        }

        existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        cartHasProductRepository.save(existingCartItem);
    }

    @Override
    public void removeProduct(Cart cart, Long id) throws IllegalAccessException {
        CartHasProduct existingCartItem = cartHasProductRepository.findById(id)
                .orElseThrow();

        if (existingCartItem.getCart() != cart) {
            throw new IllegalAccessException("The product can be only deleted by cart's owner.");
        }

        cartHasProductRepository.delete(existingCartItem);
    }

    @Override
    public double calculateTotalPrice(Cart cart) {
        List<CartHasProduct> cartItems = cartHasProductRepository.findByCart(cart);
        double totalPrice = 0.0;
        for (CartHasProduct cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }
}