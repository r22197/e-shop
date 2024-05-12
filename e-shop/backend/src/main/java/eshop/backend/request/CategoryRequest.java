package eshop.backend.request;

import java.util.Set;

public record CategoryRequest(
        Long id,
        String name,
        Long parentId,
        Set<CategoryRequest> childCategories
) {}
