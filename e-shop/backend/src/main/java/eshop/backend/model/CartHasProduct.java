package eshop.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CartHasProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer price;
    private double amount;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Product product;

    public CartHasProduct() {
    }
}
