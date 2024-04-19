package eshop.backend.controller;

import eshop.backend.dto.CartHasProductDto;
import eshop.backend.dto.UpdateProductCartQuantityDto;
import eshop.backend.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shopping-cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartHasProductDto>> getCart() {
        List<CartHasProductDto> cartHasProductDtoList = cartService.getCart().stream()
                .map(cartHasProduct -> new CartHasProductDto(
                        cartHasProduct.getId(),
                        cartHasProduct.getQuantity(),
                        cartHasProduct.getCart().getId(),
                        cartHasProduct.getProduct().getId()
                ))
                .toList();

        return ResponseEntity.ok(cartHasProductDtoList);
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<String> addToShoppingCart(@PathVariable Long id) {
        cartService.addProduct(id);
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