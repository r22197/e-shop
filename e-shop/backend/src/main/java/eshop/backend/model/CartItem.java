package eshop.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;

    public CartItem(Cart cart, Variant variant) {
        this.cart = cart;
        this.variant = variant;
        this.quantity = 1;
    }

    @JsonProperty("price")
    public BigDecimal calculateTotalBasicPriceForJson() {
        return variant.calculateBasicPriceForJson().multiply(BigDecimal.valueOf(quantity));
    }

    @JsonProperty("discounted_price")
    public BigDecimal calculateTotalDiscountedPriceForJson() {
        return variant.calculateDiscountedPriceForJson().multiply(BigDecimal.valueOf(quantity));
    }
}