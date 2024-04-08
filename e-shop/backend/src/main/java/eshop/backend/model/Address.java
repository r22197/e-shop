package eshop.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String streetName;
    private int numberOfHouse;
    private String city;
    private int zipCode;

    @ManyToOne
    private User user;

    public Address() {
    }

    public Address(Long id, String streetName, int numberOfHouse, String city, int zipCode, User user) {
        this.id = id;
        this.streetName = streetName;
        this.numberOfHouse = numberOfHouse;
        this.city = city;
        this.zipCode = zipCode;
        this.user = user;
    }

}
