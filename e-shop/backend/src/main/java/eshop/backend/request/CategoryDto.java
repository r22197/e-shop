package eshop.backend.request;

import eshop.backend.model.Category;
import eshop.backend.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data @NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private Category parentCategory;
    private Set<Product> products;
}
