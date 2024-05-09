package eshop.backend.service;

import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.model.Price;
import eshop.backend.request.PriceRequest;

import java.util.List;

public interface PriceService {
    Price create(PriceRequest request) throws VariantNotFoundException;
    List<Price> listByVariantId(Long variantId) throws VariantNotFoundException;
}
