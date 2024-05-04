package eshop.backend.service.impl;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.exception.ReviewNotFoundException;
import eshop.backend.model.Review;
import eshop.backend.repository.ProductRepository;
import eshop.backend.repository.ReviewRepository;
import eshop.backend.repository.UserRepository;
import eshop.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Review create(Long productId, Review review) throws ProductNotFoundException {
        var product = productRepository.findById(productId)
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
        persistedReview.setDateOfUpdate(LocalDateTime.now());

        return reviewRepository.save(persistedReview);
    }

    @Override
    public void delete(Long reviewId) throws ReviewNotFoundException {
        reviewRepository.deleteById(
                read(reviewId).getId()
        );
    }

    @Override
    public List<Review> list() {
        return reviewRepository.findAll();
    }

    @Override
    public Set<Review> listByProductId(Long productId) throws ProductNotFoundException {
        if (!productRepository.existsById(productId))
            throw new ProductNotFoundException(productId);

        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Set<Review> listByUserEmail(String email) {
        if (!userRepository.existsByEmail(email))
            throw new UsernameNotFoundException(email);

        return reviewRepository.findByUserEmail(email);
    }
}