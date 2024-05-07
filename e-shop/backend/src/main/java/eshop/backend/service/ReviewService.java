package eshop.backend.service;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.exception.ReviewNotFoundException;
import eshop.backend.exception.UserNotFoundException;
import eshop.backend.model.Review;
import eshop.backend.request.ReviewRequest;

import java.util.List;
import java.util.Set;

public interface ReviewService {
    Review create(ReviewRequest request) throws ProductNotFoundException, UserNotFoundException;
    Review read(Long reviewId) throws ReviewNotFoundException;
    Review update(ReviewRequest request) throws ReviewNotFoundException;
    void delete(Long reviewId) throws ReviewNotFoundException;
    List<Review> list();
}
