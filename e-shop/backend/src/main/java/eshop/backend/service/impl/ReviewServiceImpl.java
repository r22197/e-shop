package eshop.backend.service.impl;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.exception.ReviewNotFoundException;
import eshop.backend.exception.UserNotFoundException;
import eshop.backend.model.Product;
import eshop.backend.model.Review;
import eshop.backend.repository.ProductRepository;
import eshop.backend.repository.ReviewRepository;
import eshop.backend.repository.UserRepository;
import eshop.backend.request.ReviewRequest;
import eshop.backend.response.RatingSummaryResponse;
import eshop.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static eshop.backend.utils.EntityUtils.findByIdOrElseThrow;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Review create(ReviewRequest request) throws ProductNotFoundException, UserNotFoundException {
        var product = findByIdOrElseThrow(request.productId(), productRepository, ProductNotFoundException::new);
        var user = findByIdOrElseThrow(request.userId(), userRepository, UserNotFoundException::new);
        var review = new Review(request);

        review.setProduct(product);
        review.setUser(user);

        return reviewRepository.save(review);
    }

    @Override
    public Review read(Long reviewId) throws ReviewNotFoundException {
        return findByIdOrElseThrow(reviewId, reviewRepository, ReviewNotFoundException::new);
    }

    @Override
    public Review update(ReviewRequest request) throws ReviewNotFoundException {
        var review = read(request.id());

        review.setRating(request.rating());
        review.setText(request.text());
        review.setPros(request.pros());
        review.setCons(request.cons());

        return reviewRepository.save(review);
    }

    @Override
    public void delete(Long reviewId) throws ReviewNotFoundException {
        var review = read(reviewId);

        reviewRepository.delete(review);
    }

    @Override
    public RatingSummaryResponse getRatingSummary(Product product) {
        int totalReviews = reviewRepository.countAllByProduct(product);

        int[] ratingCounts = new int[5];
        int totalRatingSum = 0;

        for (int i = 1; i <= 5; i++) {
            int count = reviewRepository.countByProductAndRating(product, i);
            ratingCounts[i - 1] = count;
            totalRatingSum += count * i;
        }

        double averageRating = (double) totalRatingSum / totalReviews;

        return new RatingSummaryResponse(averageRating, ratingCounts);
    }

}