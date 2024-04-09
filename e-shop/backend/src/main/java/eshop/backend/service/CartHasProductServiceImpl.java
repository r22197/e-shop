package eshop.backend.service;

import eshop.backend.exception.UserNotFoundException;
import eshop.backend.model.Cart;
import eshop.backend.model.CartHasProduct;
import eshop.backend.model.Product;
import eshop.backend.model.User;
import eshop.backend.repository.CartHasProductRepository;
import eshop.backend.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartHasProductServiceImpl implements CartHasProductService {
    private CartHasProductRepository cartHasProductRepository;
    private CartRepository cartRepository;
    private UserService userService;

    public CartHasProductServiceImpl(CartHasProductRepository cartHasProductRepository, CartRepository cartRepository, UserService userService) {
        this.cartHasProductRepository = cartHasProductRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    @Override
    public CartHasProduct createProductInCart(CartHasProduct cartHasProduct) {
        cartHasProduct.setAmount(1);
        cartHasProduct.setPrice((int) (cartHasProduct.getProduct().getPrice() * cartHasProduct.getAmount()));


        return cartHasProductRepository.save(cartHasProduct);
    }

    @Override
    public CartHasProduct updateProductInCart(Long userId, Long id, CartHasProduct cartHasProduct) throws NullPointerException, UserNotFoundException {
        CartHasProduct newCartHasProduct = findCartHasProductById(id);
        User user = userService.findUserById(newCartHasProduct.getId());

        if (user.getId().equals(userId)) {
            newCartHasProduct.setAmount(cartHasProduct.getAmount());
            newCartHasProduct.setPrice((int) (cartHasProduct.getPrice() * newCartHasProduct.getAmount()));
        }

        return cartHasProductRepository.save(newCartHasProduct);
    }

    @Override
    public CartHasProduct isProductInCart(Cart cart, Product product) {
        return cartHasProductRepository.isProductInCart(cart, product);
    }

    @Override
    public void removeProductFromCart(Long userId, Long CartHasProductId) throws NullPointerException, UserNotFoundException {
        CartHasProduct cartHasProduct = findCartHasProductById(CartHasProductId);
        User user = userService.findUserById(cartHasProduct.getId());
        User requestedUser = userService.findUserById(userId);

        if (user.getId().equals(requestedUser.getId())) {
            cartHasProductRepository.deleteById(CartHasProductId);
        } else {
            throw new UserNotFoundException("Person not found // other");
        }
    }

    @Override
    public CartHasProduct findCartHasProductById(Long cartHasProductId) throws NullPointerException {
        Optional<CartHasProduct> productInCart = cartHasProductRepository.findById(cartHasProductId);

        return productInCart
                .orElseThrow(() -> new NullPointerException("Product is not in the cart, product id" + cartHasProductId));
    }
}