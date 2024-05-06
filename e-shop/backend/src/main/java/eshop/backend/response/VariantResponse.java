package eshop.backend.response;

import eshop.backend.model.AttributeValue;
import eshop.backend.model.Product;
import eshop.backend.model.Variant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class VariantResponse {
    private Long id;
    private Integer quantity;
    private Product product;
    private Set<AttributeValue> values;
    private double standardPrice;
    private double priceAfterDiscount;

    public VariantResponse(Variant variant) {
        this.id = variant.getId();
        this.quantity = variant.getQuantity();
        this.product = variant.getProduct();
        this.values = variant.getValues();
    }
}
