package eshop.backend.controller;

import eshop.backend.exception.UserNotFoundException;
import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.model.Wishlist;
import eshop.backend.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<Wishlist> read(@AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException {
        var email = userDetails.getUsername();
        var wishlist = wishlistService.readByUserEmail(email);

        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add/{variantId}")
    public ResponseEntity<Wishlist> addItem(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long variantId) throws UserNotFoundException, VariantNotFoundException {
        var email = userDetails.getUsername();
        var wishlist = wishlistService.addItemByUserEmail(email, variantId);

        return ResponseEntity.ok(wishlist);
    }

    @DeleteMapping("/remove/{variantId}")
    public ResponseEntity<Void> removeItem(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long variantId) throws VariantNotFoundException, UserNotFoundException {
        var email = userDetails.getUsername();
        wishlistService.removeItemByUserEmail(email, variantId);

        return ResponseEntity.noContent().build();
    }
}

