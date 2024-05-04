package eshop.backend.service.impl;

import eshop.backend.exception.AttributeNotFoundException;
import eshop.backend.model.Attribute;
import eshop.backend.repository.AttributeRepository;
import eshop.backend.service.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;

    @Override
    public Attribute create(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    @Override
    public Attribute read(Long attributeId) throws AttributeNotFoundException {
        return attributeRepository.findById(attributeId)
                .orElseThrow(() -> new AttributeNotFoundException(attributeId));
    }

    @Override
    public Attribute update(Attribute attribute) throws AttributeNotFoundException {
        var persistedAttribute = read(attribute.getId());

        persistedAttribute.setName(attribute.getName());

        return attributeRepository.save(persistedAttribute);
    }

    @Override
    public void delete(Long attributeId) throws AttributeNotFoundException {
        attributeRepository.deleteById(
                read(attributeId).getId()
        );
    }

    @Override
    public List<Attribute> list() {
        return attributeRepository.findAll();
    }
}