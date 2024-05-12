package eshop.backend.request;

import java.math.BigDecimal;
import java.util.Set;

public record VariantRequest (
    Long id,
    Integer quantity,
    Long productId,
    BigDecimal price,
    Set<Long> attributeValueIds
) {}


