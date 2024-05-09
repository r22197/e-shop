package eshop.backend.service.impl;

import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.model.Price;
import eshop.backend.repository.PriceRepository;
import eshop.backend.repository.VariantRepository;
import eshop.backend.request.PriceRequest;
import eshop.backend.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static eshop.backend.utils.EntityUtils.findByIdOrElseThrow;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final VariantRepository variantRepository;

    @Override
    public Price create(PriceRequest request) throws VariantNotFoundException {
        var variant = findByIdOrElseThrow(request.getVariantId(), variantRepository, VariantNotFoundException::new);
        var price = new Price(request);

        price.setVariant(variant);

        return priceRepository.save(price);
    }

    @Override
    public List<Price> listByVariantId(Long variantId) throws VariantNotFoundException {
        var variant = findByIdOrElseThrow(variantId, variantRepository, VariantNotFoundException::new);

        return priceRepository.findByVariantOrderByDateOfChangeAsc(variant);
    }
}
