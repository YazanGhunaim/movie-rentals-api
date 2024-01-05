package cz.cvut.fit.tjv.movie_rentals.repositories;

import cz.cvut.fit.tjv.movie_rentals.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByForenameAndEmail(String forename, String email);
}
