package eshop.backend.service;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.exception.ReviewNotFoundException;
import eshop.backend.model.Review;

import java.util.List;
import java.util.Set;

public interface ReviewService {
    Review create(Long productId, Review review) throws ProductNotFoundException;
    Review read(Long reviewId) throws ReviewNotFoundException;
    Review update(Long reviewId, Review review) throws ReviewNotFoundException;
    void delete(Long reviewId) throws ReviewNotFoundException;
    List<Review> list();
    Set<Review> listByProductId(Long productId) throws ProductNotFoundException;
    Set<Review> listByUserEmail(String email);
}
