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
import eshop.backend.service.ReviewService;
import eshop.backend.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static eshop.backend.utils.EntityUtils.findByIdOrElseThrow;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Review create(ReviewRequest request) throws ProductNotFoundException, UserNotFoundException {
        var product = findByIdOrElseThrow(request.getProductId(), productRepository, ProductNotFoundException::new);
        var user = findByIdOrElseThrow(request.getUserId(), userRepository, UserNotFoundException::new);
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
        var review = read(request.getId());

        review.setRating(request.getRating());
        review.setText(request.getText());
        review.setPros(request.getCons());
        review.setCons(request.getCons());

        return reviewRepository.save(review);
    }

    @Override
    public void delete(Long reviewId) throws ReviewNotFoundException {
        var review = read(reviewId);

        reviewRepository.delete(review);
    }
}