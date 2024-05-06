package eshop.backend.request;

import lombok.Data;

import java.util.Set;

@Data
public class VariantRequest {
    private Long id;
    private Integer quantity;
    private Long productId;
    private PriceRequest priceRequest;
    private Set<Long> attributeValueIds;
}
