package eshop.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private int level;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    public Category() {
    }

    public Category(Long id, String name, int level, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.parentCategory = parentCategory;
    }


}
