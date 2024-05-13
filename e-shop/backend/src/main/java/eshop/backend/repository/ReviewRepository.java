package eshop.backend.repository;

import eshop.backend.model.Product;
import eshop.backend.model.Review;
import eshop.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Set<Review> findByProduct(Product product);
    Set<Review> findByUser(User user);

    int countAllByProduct(Product product);

    int countByProductAndRating(Product product, int rating);


    //todo: @Transactional
    //cascade remove, else void deleteReviewByProductId
}
