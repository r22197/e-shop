package eshop.backend;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.exception.ReviewNotFoundException;
import eshop.backend.exception.UserNotFoundException;
import eshop.backend.model.Product;
import eshop.backend.model.Review;
import eshop.backend.model.User;
import eshop.backend.repository.ProductRepository;
import eshop.backend.repository.ReviewRepository;
import eshop.backend.repository.UserRepository;
import eshop.backend.request.ReviewRequest;
import eshop.backend.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Product product;
    private User user;
    private ReviewRequest reviewRequest;
    private Review review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        product = new Product();
        product.setId(1L);

        user = new User();
        user.setId(1L);

        reviewRequest = new ReviewRequest();
        reviewRequest.setId(1L);
        reviewRequest.setProductId(1L);
        reviewRequest.setUserId(1L);
        reviewRequest.setRating(5);
        reviewRequest.setText("Great product!");

        var pros = new HashSet<String>();
        pros.add("Easy to use");
        pros.add("Performance");

        var cons = new HashSet<String>();
        cons.add("only one color");

        reviewRequest.setPros(pros);
        reviewRequest.setCons(cons);

        review = new Review(reviewRequest);
        review.setId(1L);
        review.setProduct(product);
        review.setUser(user);
    }


    @Test
    void create() throws ProductNotFoundException, UserNotFoundException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(reviewRepository.save(review)).thenReturn(review);

        Review result = reviewService.create(reviewRequest);

        verify(productRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).save(review);
        assertEquals(review, result);
    }

    @Test
    void read() throws ReviewNotFoundException {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Review result = reviewService.read(1L);

        verify(reviewRepository, times(1)).findById(1L);
        assertEquals(review, result);
    }

    @Test
    void update() throws ReviewNotFoundException {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(review)).thenReturn(review);

        Review result = reviewService.update(reviewRequest);

        verify(reviewRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).save(review);
        assertEquals(review, result);
    }

    @Test
    void delete() throws ReviewNotFoundException {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        reviewService.delete(1L);

        verify(reviewRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).delete(review);
    }
}