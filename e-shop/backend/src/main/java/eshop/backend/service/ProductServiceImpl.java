package eshop.backend.service;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.model.Product;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.request.CreateProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest productRequest) {
        //todo: redo levels
        Category topLevel = getCategoryOrCreate(productRequest.getTopLevelCategory(), null, 1);
        Category secondLevel = getCategoryOrCreate(productRequest.getSecondLevelCategory(), topLevel, 2);
        Category thirdLevel = getCategoryOrCreate(productRequest.getThirdLevelCategory(), secondLevel, 3);

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(thirdLevel);

        return productRepository.save(product);
    }

    private Category getCategoryOrCreate(String categoryName, Category parentCategory, int level) {
        Category category = categoryRepository.findCategoryByNameAndParent(categoryName, parentCategory != null ? parentCategory.getName() : null);
        if (category == null) {
            category = new Category();
            category.setName(categoryName);
            category.setParentCategory(parentCategory);
            category.setLevel(level);
            category = categoryRepository.save(category);
        }
        return category;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductNotFoundException {
        Product product = findProductById(productId);
        productRepository.delete(product);

        return "successfully deleted";
    }

    @Override
    public Product updateProduct(Long productId) throws ProductNotFoundException {
        Product product = findProductById(productId);

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        return product
                .orElseThrow(() -> new ProductNotFoundException("Product does not exist with id " + id));
    }

    @Override
    public List<Product> findAllProducts(String category) {
        return null;
    }
}