package eshop.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import eshop.backend.request.VariantRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "variant")
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.REMOVE)
    private Set<Price> prices;

    @OneToMany(mappedBy = "variant")
    private Set<CartItem> cartItems;

    @OneToMany(mappedBy = "variant")
    private Set<OrderItem> orderItems;

    @ManyToMany
    @JoinTable(
            name = "variant_attribute_values",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_value_id")
    )
    private Set<AttributeValue> values;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "wishlist_variants",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "wishlist_id")
    )
    private Set<Wishlist> wishlists;

    public Variant(VariantRequest request) {
        this.id = request.getId();
        this.quantity = request.getQuantity();
    }

    @JsonProperty("currentPrice")
    public BigDecimal getCurrentPriceForJson() {
        if (prices.isEmpty())
            return BigDecimal.ZERO;

        Optional<Price> latestPrice = prices.stream()
                .max(Comparator.comparing(Price::getCreateDate));

        return latestPrice.map(Price::getPrice).orElse(BigDecimal.ZERO);
    }
}
