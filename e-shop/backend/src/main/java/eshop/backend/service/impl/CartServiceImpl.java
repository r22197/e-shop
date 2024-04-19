package eshop.backend.service.impl;

import eshop.backend.model.Cart;
import eshop.backend.model.CartHasProduct;
import eshop.backend.model.Product;
import eshop.backend.repository.CartHasProductRepository;
import eshop.backend.repository.CartRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    private final ProductRepository productRepository;
    private final CartHasProductRepository cartHasProductRepository;
    private final CartRepository cartRepository;

    public CartServiceImpl(ProductRepository productRepository, CartHasProductRepository cartHasProductRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartHasProductRepository = cartHasProductRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<CartHasProduct> getCart() {
        return cartHasProductRepository.findAll();
    }

    @Override
    public void addProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        Cart cart = cartRepository.findById(1L).orElseThrow(() -> new NoSuchElementException("Cart not found"));

        Optional<CartHasProduct> existingCartItem = cartHasProductRepository.findByProduct(product);

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
    public void updateProductCartQuantity(Long id, Integer quantity) {
        Optional<CartHasProduct> optionalCartItem = cartHasProductRepository.findById(id);
        CartHasProduct cartItem = optionalCartItem.orElseThrow(() -> new NoSuchElementException("Product not found in cart"));

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartHasProductRepository.save(cartItem);
    }

    @Override
    public void removeProduct(Long productId) {
        CartHasProduct cartHasProduct = cartHasProductRepository.findById(productId)
                .orElseThrow();

        cartHasProductRepository.delete(cartHasProduct);
    }

    @Override
    public double calculateTotalPrice() {
        List<CartHasProduct> cartItems = cartHasProductRepository.findAll();
        double totalPrice = 0.0;
        for (CartHasProduct cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }
}