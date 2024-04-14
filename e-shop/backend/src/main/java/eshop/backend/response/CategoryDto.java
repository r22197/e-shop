package eshop.backend.response;

import eshop.backend.model.Category;
import eshop.backend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private Long parent;
    private Set<Product> products;

}
