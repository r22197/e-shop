package eshop.backend.request;

import eshop.backend.model.AttributeValue;
import lombok.Data;

@Data
public class AttributeValueRequest {
    private Long id;
    private String value;
    private Long attributeId;
}
