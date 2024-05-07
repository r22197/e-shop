package eshop.backend.service;

import eshop.backend.exception.UserNotFoundException;
import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.exception.WishlistNotFoundException;
import eshop.backend.model.Wishlist;
import eshop.backend.request.WishlistRequest;

public interface WishlistService {
    Wishlist create(WishlistRequest request) throws UserNotFoundException;
    Wishlist read(Long wishlistId) throws WishlistNotFoundException;
    Wishlist addVariant(WishlistRequest request, Long variantId) throws WishlistNotFoundException, VariantNotFoundException;
    Wishlist removeVariant(WishlistRequest request, Long variantId) throws WishlistNotFoundException, VariantNotFoundException;
    void delete(Long wishlistId) throws WishlistNotFoundException;
}
