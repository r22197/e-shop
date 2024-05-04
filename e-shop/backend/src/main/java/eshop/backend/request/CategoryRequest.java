package eshop.backend.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
public class CategoryRequest {
    private Long id;
    private String name;
    private Long parent;
    private Set<CategoryRequest> childCategories = new HashSet<>();
}