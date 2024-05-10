package eshop.backend.model;


import eshop.backend.enums.DiscountType;
import eshop.backend.request.DiscountRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Min(0)
    private double amount;

    @Enumerated(EnumType.STRING)
    private DiscountType type;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "discount")
    private Set<Category> categories;

    public Discount(DiscountRequest request) {
        this.id = request.getId();
        this.name = request.getName();
        this.amount = request.getAmount();
        this.type = request.getType();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
    }
}
