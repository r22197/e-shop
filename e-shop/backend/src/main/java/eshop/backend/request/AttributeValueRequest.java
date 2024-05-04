package eshop.backend.request;

import lombok.Data;

@Data
public class AttributeValueRequest {
    private Long id;
    private String value;
    private Long attributeId;
}
