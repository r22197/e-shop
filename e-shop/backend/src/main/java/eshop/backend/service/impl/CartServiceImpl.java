package eshop.backend.service.impl;

import eshop.backend.model.Cart;
import eshop.backend.model.CartItem;
import eshop.backend.model.Product;
import eshop.backend.repository.CartItemRepository;
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
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Override
    public Cart getCartByUserEmail(String email) {

        return cartRepository.findCartByUserEmail(email);
    }

    @Override
    public void addProduct(Cart cart, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndVariantProduct(cart, product);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepository.save(cartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.getVariant().setProduct(product);
            newCartItem.setQuantity(1);
            newCartItem.setCart(cart);
            cartItemRepository.save(newCartItem);
        }
    }

    @Override
    public void updateProductCartQuantity(Cart cart, Long id, Integer quantity) throws IllegalAccessException {
        CartItem existingCartItem = cartItemRepository.findById(id)
                .orElseThrow();

        if (existingCartItem.getCart() != cart) {
            throw new IllegalAccessException("The quantity can be only changed by cart's owner.");
        }

        existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        cartItemRepository.save(existingCartItem);
    }

    @Override
    public void removeProduct(Cart cart, Long id) throws IllegalAccessException {
        CartItem existingCartItem = cartItemRepository.findById(id)
                .orElseThrow();

        if (existingCartItem.getCart() != cart) {
            throw new IllegalAccessException("The product can be only deleted by cart's owner.");
        }

        cartItemRepository.delete(existingCartItem);
    }

    @Override
    public double calculateTotalPrice(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getVariant().getProduct().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }
}