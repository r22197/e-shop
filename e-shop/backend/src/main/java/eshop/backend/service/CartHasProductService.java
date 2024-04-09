package eshop.backend.service;

import eshop.backend.exception.UserNotFoundException;
import eshop.backend.model.Cart;
import eshop.backend.model.CartHasProduct;
import eshop.backend.model.Product;

public interface CartHasProductService {
    CartHasProduct createProductInCart(CartHasProduct cartHasProduct);
    CartHasProduct updateProductInCart(Long userId, Long id, CartHasProduct cartHasProduct) throws NullPointerException, UserNotFoundException;
    CartHasProduct isProductInCart(Cart cart, Product product);

    public void removeProductFromCart(Long userId, Long CartHasProductId) throws NullPointerException, UserNotFoundException;
    public CartHasProduct findCartHasProductById(Long cartHasProductId) throws NullPointerException;

}
