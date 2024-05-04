package eshop.backend.service.impl;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.exception.ReviewNotFoundException;
import eshop.backend.model.Product;
import eshop.backend.model.Review;
import eshop.backend.repository.ProductRepository;
import eshop.backend.repository.ReviewRepository;
import eshop.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;


    @Override
    public Review create(Long productId, Review review) throws ProductNotFoundException {
        Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException(productId));

        review.setProduct(product);
        return reviewRepository.save(review);
    }

    @Override
    public Review read(Long reviewId) throws ReviewNotFoundException {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    @Override
    public Review update(Long reviewId, Review review) throws ReviewNotFoundException {
        var persistedReview = read(reviewId);

        persistedReview.setRating(review.getRating());
        persistedReview.setText(review.getText());
        persistedReview.setPros(review.getCons());
        persistedReview.setCons(review.getCons());

        return reviewRepository.save(persistedReview);
    }

    @Override
    public void delete(Long reviewId) throws ReviewNotFoundException {
        reviewRepository.deleteById(
                read(reviewId).getId()
        );
    }

    @Override
    public Set<Review> listByProductId(Long productId) throws ProductNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }

        return reviewRepository.findByProductId(productId);
    }
}
