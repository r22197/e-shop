package eshop.backend.repository;

import eshop.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Set<Review> findByUserEmail(String email);
    Set<Review> findByProductId(Long productId);

    //todo: @Transactional
    //cascade remove, else void deleteReviewByProductId
}
