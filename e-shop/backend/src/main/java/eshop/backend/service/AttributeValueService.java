package eshop.backend.service;

import eshop.backend.exception.AttributeNotFoundException;
import eshop.backend.exception.AttributeValueNotFoundException;
import eshop.backend.model.Attribute;
import eshop.backend.model.AttributeValue;
import eshop.backend.request.AttributeRequest;
import eshop.backend.request.AttributeValueRequest;

import java.util.List;

public interface AttributeValueService {
    AttributeValue create(AttributeValueRequest request) throws AttributeNotFoundException;
    AttributeValue read(Long attributeValueId) throws AttributeValueNotFoundException;
    AttributeValue update(AttributeValueRequest request) throws AttributeValueNotFoundException;
    void delete(Long attributeValueId) throws AttributeValueNotFoundException;
    List<AttributeValue> listByAttributeId(Long attributeId) throws AttributeNotFoundException;
}