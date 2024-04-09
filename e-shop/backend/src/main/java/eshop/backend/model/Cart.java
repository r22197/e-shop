package eshop.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Cart {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    private Set<CartHasProduct> cartHasProducts = new HashSet<>();

    private int totalQuantity;
    private double totalPrice;

    public Cart() {
    }
}
