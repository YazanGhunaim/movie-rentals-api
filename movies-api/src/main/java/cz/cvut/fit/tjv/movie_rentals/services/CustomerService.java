package cz.cvut.fit.tjv.movie_rentals.services;

import cz.cvut.fit.tjv.movie_rentals.domain.Customer;

import java.util.Optional;

public interface CustomerService extends CrudService<Customer, Long> {
    void isValid(Customer customer);

    Optional<Customer> readByForenameAndEmail(String forename, String email);
}
