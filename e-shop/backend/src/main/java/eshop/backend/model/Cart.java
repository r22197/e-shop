package eshop.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addItem(CartItem item) {
        this.cartItems.add(item);
    }

    public void removeItem(CartItem item) {
        this.cartItems.remove(item);
    }

    @JsonProperty("totalPrice")
    public BigDecimal getTotalPriceForJson() {
        return cartItems.stream()
                .map(CartItem::getTotalPriceInclQuantityForJson)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
