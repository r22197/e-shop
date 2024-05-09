package eshop.backend.model;

import eshop.backend.request.PriceRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    private BigDecimal price;
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;

    public Price(PriceRequest request) {
        this.id = request.getId();
        this.price = request.getPrice();
        this.createDate = LocalDateTime.now();
    }
}
