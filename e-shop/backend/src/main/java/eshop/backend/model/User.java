package eshop.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String role;

    @OneToMany
    private List<Address> addresses = new ArrayList<>();

    private LocalDateTime dateOfRegistration;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, String phoneNumber, String password, String role, List<Address> addresses, LocalDateTime dateOfRegistration) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.addresses = addresses;
        this.dateOfRegistration = dateOfRegistration;
    }

}
