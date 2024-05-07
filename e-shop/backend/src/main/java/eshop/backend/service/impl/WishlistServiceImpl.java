package eshop.backend.service.impl;

import eshop.backend.exception.UserNotFoundException;
import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.exception.WishlistNotFoundException;
import eshop.backend.model.Wishlist;
import eshop.backend.repository.UserRepository;
import eshop.backend.repository.VariantRepository;
import eshop.backend.repository.WishlistRepository;
import eshop.backend.request.WishlistRequest;
import eshop.backend.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static eshop.backend.utils.EntityUtils.findByIdOrElseThrow;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final VariantRepository variantRepository;

    @Override
    public Wishlist create(WishlistRequest request) throws UserNotFoundException {
        var user = findByIdOrElseThrow(request.getUserId(), userRepository, UserNotFoundException::new);
        var wishlist = new Wishlist(request);

        wishlist.setUser(user);

        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist read(Long wishlistId) throws WishlistNotFoundException {
        return findByIdOrElseThrow(wishlistId, wishlistRepository, WishlistNotFoundException::new);
    }

    @Override
    public Wishlist addVariant(WishlistRequest request, Long variantId) throws WishlistNotFoundException, VariantNotFoundException {
        var wishlist = read(request.getId());
        var variant = findByIdOrElseThrow(request.getVariantId(), variantRepository, VariantNotFoundException::new);

        wishlist.addVariant(variant);

        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist removeVariant(WishlistRequest request, Long variantId) throws WishlistNotFoundException, VariantNotFoundException {
        var wishlist = read(request.getId());
        var variant = findByIdOrElseThrow(request.getVariantId(), variantRepository, VariantNotFoundException::new);

        wishlist.removeVariant(variant);

        return wishlistRepository.save(wishlist);
    }

    @Override
    public void delete(Long wishlistId) throws WishlistNotFoundException {
        var wishlist = read(wishlistId);

        wishlistRepository.delete(wishlist);
    }
}