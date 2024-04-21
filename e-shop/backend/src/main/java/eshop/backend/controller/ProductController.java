package eshop.backend.controller;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.mapper.ProductMapper;
import eshop.backend.model.Product;
import eshop.backend.dto.ProductDto;
import eshop.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ProductDto> productDtoPages = productService
                .getAllProducts(pageNumber, pageSize)
                .map(productMapper::convertToDto);

        return ResponseEntity.ok(productDtoPages);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam String query) {
        List<ProductDto> productDtoList = productService
                .searchProducts(query)
                .stream()
                .map(productMapper::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDtoList);
    }



    @GetMapping("/category/{id}")
    public ResponseEntity<Page<ProductDto>> getProductsInCategory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "asc") String sortBy,
            @RequestParam(required = false) Double lowPrice,
            @RequestParam(required = false) Double maxPrice) throws CategoryNotFoundException {

        Page<Product> productPage = productService.getProductsInCategory(id, pageNumber, pageSize, sortBy, lowPrice, maxPrice);
        Page<ProductDto> productDtoPage = productPage.map(productMapper::convertToDto);
        return ResponseEntity.ok(productDtoPage);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        Product product = productService.getById(id);
        ProductDto productDto = productMapper.convertToDto(product);

        return ResponseEntity.ok(productDto);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = productMapper.convertToEntity(productDto);
        productService.create(product);

        return new ResponseEntity<>(productDto, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) throws ProductNotFoundException {
        if (!Objects.equals(id, productDto.getId())) {
            throw new ProductNotFoundException("ID of the product is not equal to the one being updated.");
        }
        Product product = productMapper.convertToEntity(productDto);
        productService.update(id, product);

        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        productService.delete(id);

        return ResponseEntity.ok().build();
    }
}