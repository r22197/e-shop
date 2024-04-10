package eshop.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Data @NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    private String description;

    @NotNull
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;

    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<CartHasProduct> productInCarts;
}
