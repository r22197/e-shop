package eshop.backend.controller;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
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

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Product> productDtoPages = productService.list(pageNumber, pageSize);

        return ResponseEntity.ok(productDtoPages);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
        List<Product> productRequestList = productService.search(query);

        return ResponseEntity.ok(productRequestList);
    }



    @GetMapping("/category/{id}")
    public ResponseEntity<Page<Product>> getProductsInCategory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "asc") String sortBy,
            @RequestParam(required = false) Double lowPrice,
            @RequestParam(required = false) Double maxPrice) throws CategoryNotFoundException {

        Page<Product> productPage = productService.listByCategory(id, pageNumber, pageSize, sortBy, lowPrice, maxPrice);
        return ResponseEntity.ok(productPage);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        Product product = productService.read(id);

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductRequest> createProduct(@Valid @RequestBody ProductRequest productRequest) throws CategoryNotFoundException {
        productService.create(productRequest);

        return new ResponseEntity<>(productRequest, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRequest> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) throws ProductNotFoundException, CategoryNotFoundException {
        if (!Objects.equals(id, productRequest.getId())) {
            throw new ProductNotFoundException(id);
        }
        productService.update(productRequest);

        return ResponseEntity.ok(productRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        productService.delete(id);

        return ResponseEntity.ok().build();
    }
}