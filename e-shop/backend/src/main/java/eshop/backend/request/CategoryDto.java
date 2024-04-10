package eshop.backend.request;

import eshop.backend.model.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private Category parentCategory;
}
