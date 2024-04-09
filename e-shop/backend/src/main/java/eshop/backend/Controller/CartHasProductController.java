package eshop.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import eshop.backend.model.CartHasProduct;
import eshop.backend.service.CartHasProductService;

@RestController
@RequestMapping("/api/cartHasProduct")
public class CartHasProductController {

    @Autowired
    private CartHasProductService cartHasProductService;

    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        cartHasProductService.addProductToCart(cartId, productId, quantity);
        return new ResponseEntity<>("Produkt byl úspěšně přidán do košíku.", HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeProductFromCart(@RequestParam Long cartHasProductId) {
        cartHasProductService.removeProductFromCart(cartHasProductId);
        return new ResponseEntity<>("Produkt byl úspěšně odebrán z košíku.", HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Iterable<CartHasProduct>> getCartProducts(@PathVariable Long cartId) {
        Iterable<CartHasProduct> cartProducts = cartHasProductService.getCartProducts(cartId);
        return ResponseEntity.ok(cartProducts);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProductQuantity(@RequestParam Long cartHasProductId, @RequestParam int quantity) {
        cartHasProductService.updateProductQuantity(cartHasProductId, quantity);
        return new ResponseEntity<>("Množství produktu v košíku bylo úspěšně aktualizováno.", HttpStatus.OK);
    }
}
