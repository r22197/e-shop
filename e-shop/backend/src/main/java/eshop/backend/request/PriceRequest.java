package eshop.backend.request;

import lombok.Data;

@Data
public class PriceRequest {
    private Long id;
    private double price;
    private Long VariantId;
}
