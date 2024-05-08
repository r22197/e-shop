package eshop.backend.controller;

import eshop.backend.exception.UserNotFoundException;
import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.request.UpdateProductCartQuantityDto;
import eshop.backend.model.Cart;
import eshop.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopping-cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> read(@AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException, VariantNotFoundException {
        String email = userDetails.getUsername();
        Cart cart = cartService.readByUserEmail(email);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add/{variantId}")
    public ResponseEntity<Cart> addItem(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long variantId) throws UserNotFoundException, VariantNotFoundException {
        String email = userDetails.getUsername();
        Cart cart = cartService.addItemByUserEmail(email, variantId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/{variantId}")
    public ResponseEntity<Cart> updateItemQuantity(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long variantId, @RequestBody UpdateProductCartQuantityDto dto) throws UserNotFoundException, VariantNotFoundException {
        String email = userDetails.getUsername();
        Cart cart = cartService.updateItemQuantityByUserEmail(email, variantId, dto.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{variantId}")
    public ResponseEntity<Void> removeItem(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long variantId) throws UserNotFoundException, VariantNotFoundException {
        String email = userDetails.getUsername();
        cartService.removeItemByUserEmail(email, variantId);
        return ResponseEntity.noContent().build();
    }
}
