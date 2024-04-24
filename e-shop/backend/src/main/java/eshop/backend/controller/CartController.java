package eshop.backend.controller;

import eshop.backend.dto.CartDto;
import eshop.backend.dto.UpdateProductCartQuantityDto;
import eshop.backend.mapper.CartMapper;
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
    private  final CartMapper cartMapper;

    @GetMapping
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Cart cart = cartService.getCartByUserEmail(email);

        CartDto cartDto = cartMapper.convertCartToDto(cart, cartService.calculateTotalPrice(cart));

        return ResponseEntity.ok(cartDto);
    }


    @PostMapping("/add/{id}")
    public ResponseEntity<String> addToShoppingCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        String email = userDetails.getUsername();
        Cart cart = cartService.getCartByUserEmail(email);

        cartService.addProduct(cart, id);
        return ResponseEntity.ok("Item added to the shopping cart.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductCartQuantity(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestBody UpdateProductCartQuantityDto dto) throws IllegalAccessException {
        String email = userDetails.getUsername();
        Cart cart = cartService.getCartByUserEmail(email);

        cartService.updateProductCartQuantity(cart, id, dto.getQuantity());

        return ResponseEntity.ok("quantity");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeFromShoppingCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) throws IllegalAccessException {
        String email = userDetails.getUsername();
        Cart cart = cartService.getCartByUserEmail(email);

        cartService.removeProduct(cart, id);
        return ResponseEntity.ok("Item removed from the shopping cart.");
    }
}