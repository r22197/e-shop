package eshop.backend.model;

import eshop.backend.request.ReviewRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    @NotNull //todo: try
    private int rating;

    private String text;

    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfUpdate;

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

    public Review() {
        this.dateOfCreation = LocalDateTime.now();
    }

    public Review(ReviewRequest request) {
        this.id = request.getId();
        this.rating = request.getRating();
        this.text = request.getText();
        this.pros = request.getPros();
        this.cons = request.getCons();
        this.dateOfCreation = LocalDateTime.now();
    }
}
