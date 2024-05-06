package eshop.backend.service.impl;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.model.Discount;
import eshop.backend.model.Variant;
import eshop.backend.repository.AttributeValueRepository;
import eshop.backend.repository.PriceRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.repository.VariantRepository;
import eshop.backend.request.VariantRequest;
import eshop.backend.response.VariantResponse;
import eshop.backend.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VariantServiceImpl implements VariantService {
    private final VariantRepository variantRepository;
    private final ProductRepository productRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final PriceRepository priceRepository;
    private final PriceServiceImpl priceService;

    @Override
    public Variant create(VariantRequest request) throws ProductNotFoundException {
        var product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));

        var variant = new Variant(request);
        variant.setProduct(product);
        manageAttributeValuesIfExist(variant, request);

        return variantRepository.save(variant);
    }

    @Override
    public VariantResponse read(Long variantId) throws VariantNotFoundException {
        var variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new VariantNotFoundException(variantId));

        double priceValue = priceService.readLastPriceByVariantId(variantId).getPrice();

        VariantResponse response = new VariantResponse(variant);
        response.setStandardPrice(priceValue);
        setDiscountPriceIfExists(response);

        return response;
    }

    @Override
    public Variant update(VariantRequest request) throws VariantNotFoundException {
        var persistedVariant = variantRepository.findById(request.getId())
                .orElseThrow(() -> new VariantNotFoundException(request.getProductId()));

        persistedVariant.setQuantity(request.getQuantity());
        manageAttributeValuesIfExist(persistedVariant, request);
        findIfPriceChangedThenCreate(request, persistedVariant);

        return variantRepository.save(persistedVariant);
    }

    @Override
    public void delete(Long variantId) throws VariantNotFoundException {
        var variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new VariantNotFoundException(variantId));

        priceRepository.deleteAllByVariant(variant);
        variantRepository.delete(variant);
    }

    @Override
    public List<Variant> list() {
        return variantRepository.findAll(); //todo
    }

    private void setDiscountPriceIfExists(VariantResponse response) throws VariantNotFoundException {
        Discount discount = response.getProduct().getCategory().getDiscount();
        double priceValue = priceService.readLastPriceByVariantId(response.getId()).getPrice();
        boolean isCategoryDiscounted = discount != null;

        if (isCategoryDiscounted)
            response.setPriceAfterDiscount(priceValue * (1 - discount.getAmount()));
    }

    private void manageAttributeValuesIfExist(Variant variant, VariantRequest request) {
        boolean isAttributeValuesNotEmpty = request.getAttributeValueIds() != null && !request.getAttributeValueIds().isEmpty();

        if (isAttributeValuesNotEmpty) {
            var attributeValues = attributeValueRepository.findAllById(request.getAttributeValueIds());
            variant.setValues(new HashSet<>(attributeValues));
        }
    }

    private void findIfPriceChangedThenCreate(VariantRequest request, Variant persistedVariant) throws VariantNotFoundException {
        double price = priceService.readLastPriceByVariantId(persistedVariant.getId()).getPrice();
        boolean isNotIdenticalAsLastPrice = price != request.getPriceRequest().getPrice();

        if (isNotIdenticalAsLastPrice) {
            priceService.create(request.getPriceRequest());
        }
    }
}
