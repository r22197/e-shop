package eshop.backend.controller;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Cart;
import eshop.backend.model.CartHasProduct;
import eshop.backend.model.Product;
import eshop.backend.request.ProductDto;
import eshop.backend.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartHasProduct>> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<String> addToShoppingCart(@PathVariable Long id) {
        cartService.addProduct(id);
        return ResponseEntity.ok("Item added to the shopping cart.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartHasProduct> updateProductCartQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        CartHasProduct updatedProductCart = cartService.updateProductCartQuantity(id, quantity);

        return ResponseEntity.ok(updatedProductCart);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeFromShoppingCart(@PathVariable Long id) {
        cartService.removeProduct(id);
        return ResponseEntity.ok("Item removed from the shopping cart.");
    }
}