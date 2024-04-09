package eshop.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import eshop.backend.model.Cart;
import eshop.backend.model.User;
import eshop.backend.service.ShoppingCartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        User user = // Získání uživatele dle userId (například pomocí UserRepository)
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Uživatel s ID " + userId + " nebyl nalezen.");
        }

        shoppingCartService.addToCart(user, productId, quantity);

        return new ResponseEntity<>("Položka byla úspěšně přidána do košíku.", HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        User user = // Získání uživatele dle userId (například pomocí UserRepository)
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Uživatel s ID " + userId + " nebyl nalezen.");
        }

        shoppingCartService.removeFromCart(user, productId);

        return new ResponseEntity<>("Položka byla úspěšně odebrána z košíku.", HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getUserCart(@PathVariable Long userId) {
        User user = // Získání uživatele dle userId (například pomocí UserRepository)
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Cart cart = shoppingCartService.getUserCart(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestParam Long userId) {
        User user = // Získání uživatele dle userId (například pomocí UserRepository)
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Uživatel s ID " + userId + " nebyl nalezen.");
        }

        shoppingCartService.checkout(user);
        return new ResponseEntity<>("Objednávka byla úspěšně provedena.", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCartItemQuantity(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        User user = // Získání uživatele dle userId (například pomocí UserRepository)
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Uživatel s ID " + userId + " nebyl nalezen.");
        }

        shoppingCartService.updateCartItemQuantity(user, productId, quantity);
        return new ResponseEntity<>("Množství položky v košíku bylo úspěšně aktualizováno.", HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestParam Long userId) {
        User user = // Získání uživatele dle userId (například pomocí UserRepository)
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Uživatel s ID " + userId + " nebyl nalezen.");
        }

        shoppingCartService.clearCart(user);
        return new ResponseEntity<>("Košík byl úspěšně vyčištěn.", HttpStatus.OK);
    }
}
