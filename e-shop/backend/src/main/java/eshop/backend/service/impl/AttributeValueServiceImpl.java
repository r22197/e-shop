package eshop.backend.service.impl;

import eshop.backend.exception.AttributeNotFoundException;
import eshop.backend.exception.AttributeValueNotFoundException;
import eshop.backend.model.AttributeValue;
import eshop.backend.repository.AttributeRepository;
import eshop.backend.repository.AttributeValueRepository;
import eshop.backend.request.AttributeValueRequest;
import eshop.backend.service.AttributeValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeValueServiceImpl implements AttributeValueService {
    private final AttributeValueRepository valueRepository;
    private final AttributeRepository attributeRepository;

    @Override
    public AttributeValue create(AttributeValueRequest request) throws AttributeNotFoundException {
        var attribute = attributeRepository.findById(request.attributeId())
                .orElseThrow(() -> new AttributeNotFoundException(request.attributeId()));

        var value = new AttributeValue(request);
        value.setAttribute(attribute);

        return valueRepository.save(value);
    }

    @Override
    public AttributeValue read(Long attributeValueId) throws AttributeValueNotFoundException {
        return valueRepository.findById(attributeValueId)
                .orElseThrow(() -> new AttributeValueNotFoundException(attributeValueId));
    }

    @Override
    public AttributeValue update(AttributeValueRequest request) throws AttributeValueNotFoundException {
        var persistedValue = read(request.id());

        persistedValue.setValue(request.value());

        return valueRepository.save(persistedValue);
    }

    @Override
    public void delete(Long attributeValueId) throws AttributeValueNotFoundException {
        valueRepository.deleteById(
                read(attributeValueId).getId()
        );
    }

    @Override
    public List<AttributeValue> listByAttributeId(Long attributeId) throws AttributeNotFoundException {
        var attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new AttributeNotFoundException(attributeId));

        return valueRepository.findByAttribute(attribute);
    }
    //todo: check jestli je nutné
}
