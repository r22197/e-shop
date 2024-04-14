package eshop.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.response.ProductDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    // source https://stackoverflow.com/questions/24994440/no-serializer-found-for-class-org-hibernate-proxy-pojo-javassist-javassist
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<CartHasProduct> productInCarts;
}