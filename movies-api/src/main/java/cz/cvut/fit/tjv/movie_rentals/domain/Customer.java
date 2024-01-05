package cz.cvut.fit.tjv.movie_rentals.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Collection;
import java.util.Objects;

/*
Customer class: Describes customer entity
                a customer has an ID, forename, surname and an email
                as well for a collection of movies for which he rented
*/
@Entity
public class Customer implements EntityWithId<Long> {
    @Id
    @GeneratedValue
    private Long customerID;
    private String forename;
    private String surname;
    private String email;

    @ManyToMany
    private Collection<Movie> rentedMovies;

    // Constructor
    public Customer(Long customerID, String forename, String surname, String email) {
        this.customerID = customerID;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
    }

    // no-arg constructor for data jpa
    public Customer() {
    }


    // Getters and Setters

    // EntityWithID interface override
    @Override
    public Long getID() {
        return customerID;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Movie> getRentedMovies() {
        return rentedMovies;
    }

    public void setRentedMovies(Collection<Movie> rentedMovies) {
        this.rentedMovies = rentedMovies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerID, customer.customerID) && Objects.equals(forename, customer.forename) && Objects.equals(surname, customer.surname) && Objects.equals(email, customer.email) && Objects.equals(rentedMovies, customer.rentedMovies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerID, forename, surname, email, rentedMovies);
    }
}
