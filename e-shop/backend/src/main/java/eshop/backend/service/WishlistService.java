package eshop.backend.service;

import eshop.backend.exception.UserNotFoundException;
import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.exception.WishlistNotFoundException;
import eshop.backend.model.Wishlist;

public interface WishlistService {
    Wishlist createByUserEmail(String email) throws UserNotFoundException;
    Wishlist readByUserEmail(String email) throws UserNotFoundException;
    Wishlist addItemByUserEmail(String email, Long variantId) throws VariantNotFoundException, UserNotFoundException;
    void removeItemByUserEmail(String email, Long variantId) throws VariantNotFoundException, UserNotFoundException;
    void delete(Long wishlistId) throws WishlistNotFoundException;
}
