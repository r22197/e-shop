package eshop.backend.service.impl;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.exception.ReviewNotFoundException;
import eshop.backend.exception.UserNotFoundException;
import eshop.backend.model.Review;
import eshop.backend.repository.ProductRepository;
import eshop.backend.repository.ReviewRepository;
import eshop.backend.repository.UserRepository;
import eshop.backend.request.ReviewRequest;
import eshop.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
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
    public Review create(ReviewRequest request) throws ProductNotFoundException, UserNotFoundException {
        var product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));

        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        var review = new Review(request);
        review.setProduct(product);
        review.setUser(user);

        return reviewRepository.save(review);
    }

    @Override
    public Review read(Long reviewId) throws ReviewNotFoundException {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    @Override
    public Review update(ReviewRequest request) throws ReviewNotFoundException {
        var persistedReview = read(request.getId());

        persistedReview.setRating(request.getRating());
        persistedReview.setText(request.getText());
        persistedReview.setPros(request.getCons());
        persistedReview.setCons(request.getCons());
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
    public Set<Review> listByUserId(Long userId) throws UserNotFoundException {
        if (!userRepository.existsById(userId))
            throw new UserNotFoundException(userId);

        return reviewRepository.findByUserId(userId);
    }
}