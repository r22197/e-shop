package eshop.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private Long parent;
    private Set<CategoryDto> childCategories = new HashSet<>();
}