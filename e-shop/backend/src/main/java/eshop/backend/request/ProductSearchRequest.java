package eshop.backend.request;

import java.math.BigDecimal;
import java.util.Set;

public record ProductSearchRequest( //todo zkusit + příp. dát i na ostatní requesty
        BigDecimal lowPrice,
        BigDecimal maxPrice,
        Set<Long> attributeValueIds
) {}