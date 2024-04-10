package eshop.backend.service.impl;

import eshop.backend.model.CartHasProduct;
import eshop.backend.model.Product;
import eshop.backend.repository.CartHasProductRepository;
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

    public CartServiceImpl(ProductRepository productRepository, CartHasProductRepository cartHasProductRepository) {
        this.productRepository = productRepository;
        this.cartHasProductRepository = cartHasProductRepository;
    }

    @Override
    public void addProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));


        Optional<CartHasProduct> optionalCartItem = cartHasProductRepository.findByProduct(product);

        if (optionalCartItem.isPresent()) {
            CartHasProduct cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartHasProductRepository.save(cartItem);
        } else {
            CartHasProduct newCartItem = new CartHasProduct();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(1);
            cartHasProductRepository.save(newCartItem);
        }
    }

    @Override
    public void removeProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        Optional<CartHasProduct> optionalCartItem = cartHasProductRepository.findByProduct(product);

        if (optionalCartItem.isPresent()) {
            CartHasProduct cartItem = optionalCartItem.get();
            int newQuantity = cartItem.getQuantity() - 1;

            if (newQuantity > 0) {
                cartItem.setQuantity(newQuantity);
                cartHasProductRepository.save(cartItem);
            } else {
                cartHasProductRepository.delete(cartItem);
            }
        } else {
            throw new NoSuchElementException("Product not found in cart");
        }
    }

    @Override
    public List<CartHasProduct> getCart() {
        return cartHasProductRepository.findAll();
    }
}

