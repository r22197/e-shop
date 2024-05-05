package eshop.backend.service.impl;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.model.Variant;
import eshop.backend.repository.ProductRepository;
import eshop.backend.repository.VariantRepository;
import eshop.backend.request.VariantRequest;
import eshop.backend.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariantServiceImpl implements VariantService {
    private final VariantRepository variantRepository;
    private final ProductRepository productRepository;

    @Override
    public Variant create(VariantRequest request) throws ProductNotFoundException {
        var product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));

        var variant = new Variant(request);
        variant.setProduct(product);

        return variantRepository.save(variant);
    }

    @Override
    public Variant read(Long variantId) throws VariantNotFoundException {
        return variantRepository.findById(variantId)
                .orElseThrow(() -> new VariantNotFoundException(variantId));
    }

    @Override
    public Variant update(VariantRequest request) throws VariantNotFoundException {
        var persistedVariant = read(request.getId());

        persistedVariant.setQuantity(request.getQuantity());
        //todo price, values, ..

        return variantRepository.save(persistedVariant);
    }

    @Override
    public void delete(Long variantId) throws VariantNotFoundException {
        variantRepository.deleteById(
                read(variantId).getId()
        );
    }

    @Override
    public List<Variant> list() {
        return variantRepository.findAll();
    }
}
