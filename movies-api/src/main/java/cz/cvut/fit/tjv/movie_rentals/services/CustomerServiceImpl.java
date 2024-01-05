package cz.cvut.fit.tjv.movie_rentals.services;

import cz.cvut.fit.tjv.movie_rentals.domain.Customer;
import cz.cvut.fit.tjv.movie_rentals.repositories.CustomerRepository;
import cz.cvut.fit.tjv.movie_rentals.utilities.CustomerEmailException;
import cz.cvut.fit.tjv.movie_rentals.utilities.CustomerMovieException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class CustomerServiceImpl extends CrudServiceImpl<CustomerRepository, Customer, Long> implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> readByForenameAndEmail(String forename, String email) {
        return customerRepository.findByForenameAndEmail(forename, email);
    }

    // I don't allow alteration of customers rented movies except from the rent movie functionality
    @Override
    public void update(Long id, Customer entity) {
        if (id == null || entity == null)
            throw new IllegalArgumentException("Invalid entity/id");

        Optional<Customer> existingEntityOptional = getRepository().findById(id);
        if (existingEntityOptional.isPresent()) {
            Customer existingCustomer = existingEntityOptional.get();
            existingCustomer.setForename(entity.getForename());
            existingCustomer.setSurname(entity.getSurname());
            existingCustomer.setEmail(entity.getEmail());
            customerRepository.save(existingCustomer);
        } else {
            throw new EntityNotFoundException("Could not find entity with ID: " + id);
        }
    }

    // A newly created customer must not have any rented movies
    @Override
    public void isValid(Customer customer) {
        boolean validEmail = Pattern.compile
                        (
                                "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                                Pattern.CASE_INSENSITIVE
                        )
                .matcher(customer.getEmail()).matches();

        if (!customer.getRentedMovies().isEmpty())
            throw new CustomerMovieException();

        if (!validEmail)
            throw new CustomerEmailException();
    }

    @Override
    protected CustomerRepository getRepository() {
        return customerRepository;
    }
}
