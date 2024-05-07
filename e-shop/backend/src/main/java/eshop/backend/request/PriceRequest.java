package eshop.backend.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceRequest {
    private Long id;
    private BigDecimal price;
    private Long VariantId;
}
