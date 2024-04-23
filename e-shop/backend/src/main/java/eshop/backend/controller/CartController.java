package eshop.backend.controller;

import eshop.backend.dto.CartHasProductDto;
import eshop.backend.dto.UpdateProductCartQuantityDto;
import eshop.backend.model.Cart;
import eshop.backend.model.User;
import eshop.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartHasProductDto>> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Cart cart = cartService.getCartByUserEmail(email);

        if (cart != null && cart.getProductsInCart() != null && !cart.getProductsInCart().isEmpty()) {
            List<CartHasProductDto> cartHasProductDtoList = cart.getProductsInCart().stream()
                    .map(cartHasProduct -> new CartHasProductDto(
                            cartHasProduct.getId(),
                            cartHasProduct.getQuantity(),
                            cartHasProduct.getCart().getId(),
                            cartHasProduct.getProduct().getId()
                    ))
                    .toList();
            return ResponseEntity.ok(cartHasProductDtoList);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }


    @PostMapping("/add/{id}")
    public ResponseEntity<String> addToShoppingCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        String email = userDetails.getUsername();
        Cart cart = cartService.getCartByUserEmail(email);

        cartService.addProduct(cart, id);
        return ResponseEntity.ok("Item added to the shopping cart.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductCartQuantity(@PathVariable Long id, @RequestBody UpdateProductCartQuantityDto dto) {
        cartService.updateProductCartQuantity(id, dto.getQuantity());

        return ResponseEntity.ok("quantity");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeFromShoppingCart(@PathVariable Long id) {
        cartService.removeProduct(id);
        return ResponseEntity.ok("Item removed from the shopping cart.");
    }

    @GetMapping("/total-price")
    public ResponseEntity<Double> getTotalPrice() {
        double totalPrice = cartService.calculateTotalPrice();
        return ResponseEntity.ok(totalPrice);
    }
}