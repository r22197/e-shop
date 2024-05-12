package eshop.backend.model;

import eshop.backend.request.ReviewRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1) @Max(5)
    private int rating;

    private String text;

    @CreationTimestamp
    @Column(updatable = false, insertable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updateDate;

    @ElementCollection
    private Set<String> pros;

    @ElementCollection
    private Set<String> cons;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Review(ReviewRequest request) {
        this.id = request.id();
        this.rating = request.rating();
        this.text = request.text();
        this.pros = request.pros();
        this.cons = request.cons();
    }
}
