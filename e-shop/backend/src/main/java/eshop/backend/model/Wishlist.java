package eshop.backend.model;

import eshop.backend.request.WishlistRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "wishlist_variants",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "variant_id")
    )
    private Set<Variant> variants;

    public Wishlist(WishlistRequest request) {
        this.id = request.getId();
    }

    public void addVariant(Variant variant) {
        variants.add(variant);
    }

    public void removeVariant(Variant variant) {
        variants.remove(variant);
    }
}
