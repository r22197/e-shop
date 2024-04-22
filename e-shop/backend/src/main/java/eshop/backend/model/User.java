package eshop.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;

    private LocalDateTime dateOfRegistration;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    public User() {
        this.dateOfRegistration = LocalDateTime.now();
    }
}