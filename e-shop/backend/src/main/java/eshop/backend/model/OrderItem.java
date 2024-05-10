package eshop.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;

    public OrderItem(CartItem cartItem) {
        this.quantity = cartItem.getQuantity();
        this.variant = cartItem.getVariant();
    }

    @JsonProperty("priceInclQuantityByDate")
    public BigDecimal getPriceInclQuantityByDateForJson() {
        Optional<PriceHistory> closestPrice = variant.getPriceHistories().stream()
                .min(Comparator.comparingLong(p -> Math.abs(ChronoUnit.DAYS.between(order.getCreateDate(), LocalDateTime.now()))));

        if (closestPrice.isEmpty())
            return BigDecimal.ZERO;

        BigDecimal priceValue = closestPrice.get().getPrice();
        return priceValue.multiply(BigDecimal.valueOf(quantity));
    }

}
