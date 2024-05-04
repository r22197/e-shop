package eshop.backend.controller;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.mapper.ProductMapper;
import eshop.backend.model.Product;
import eshop.backend.request.ProductRequest;
import eshop.backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<Page<ProductRequest>> getAllProducts(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ProductRequest> productDtoPages = productService
                .list(pageNumber, pageSize)
                .map(productMapper::convertToDto);

        return ResponseEntity.ok(productDtoPages);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductRequest>> searchProducts(@RequestParam String query) {
        List<ProductRequest> productRequestList = productService
                .search(query)
                .stream()
                .map(productMapper::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productRequestList);
    }



    @GetMapping("/category/{id}")
    public ResponseEntity<Page<ProductRequest>> getProductsInCategory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "asc") String sortBy,
            @RequestParam(required = false) Double lowPrice,
            @RequestParam(required = false) Double maxPrice) throws CategoryNotFoundException {

        Page<Product> productPage = productService.listByCategory(id, pageNumber, pageSize, sortBy, lowPrice, maxPrice);
        Page<ProductRequest> productDtoPage = productPage.map(productMapper::convertToDto);
        return ResponseEntity.ok(productDtoPage);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductRequest> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        Product product = productService.read(id);
        ProductRequest productRequest = productMapper.convertToDto(product);

        return ResponseEntity.ok(productRequest);
    }

    @PostMapping
    public ResponseEntity<ProductRequest> createProduct(@Valid @RequestBody ProductRequest productRequest) throws CategoryNotFoundException {
        Product product = productMapper.convertToEntity(productRequest);
        productService.create(product);

        return new ResponseEntity<>(productRequest, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRequest> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) throws ProductNotFoundException, CategoryNotFoundException {
        if (!Objects.equals(id, productRequest.getId())) {
            throw new ProductNotFoundException(id);
        }
        Product product = productMapper.convertToEntity(productRequest);
        productService.update(product);

        return ResponseEntity.ok(productRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        productService.delete(id);

        return ResponseEntity.ok().build();
    }
}