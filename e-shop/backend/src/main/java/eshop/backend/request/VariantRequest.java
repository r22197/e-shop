package eshop.backend.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class VariantRequest {
    private Long id;
    private Integer quantity;
    private Long productId;
    private BigDecimal price;
    private Set<Long> attributeValueIds;
}
