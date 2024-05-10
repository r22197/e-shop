package eshop.backend.service;

import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.model.PriceHistory;
import eshop.backend.request.PriceRequest;

import java.util.List;

public interface PriceService {
    PriceHistory create(PriceRequest request) throws VariantNotFoundException;
    List<PriceHistory> listByVariantId(Long variantId) throws VariantNotFoundException;
}
