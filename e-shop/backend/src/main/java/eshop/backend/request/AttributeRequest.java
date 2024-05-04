package eshop.backend.request;

import lombok.Data;

import java.util.Set;

@Data
public class AttributeRequest {
    private Long id;
    private String name;
    private Set<Long> valueIds; //todo
}
