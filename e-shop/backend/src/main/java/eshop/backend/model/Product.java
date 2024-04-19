package eshop.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    private String name;
    private String description;

    @NotNull
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    // source https://stackoverflow.com/questions/24994440/no-serializer-found-for-class-org-hibernate-proxy-pojo-javassist-javassist
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<CartHasProduct> productsInCart;

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Number cannot be negative.");
        }
        this.price = price;
    }
}