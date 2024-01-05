package cz.cvut.fit.tjv.movie_rentals.controllers;

import cz.cvut.fit.tjv.movie_rentals.domain.Customer;
import cz.cvut.fit.tjv.movie_rentals.services.CustomerService;
import cz.cvut.fit.tjv.movie_rentals.utilities.CustomerMovieException;
import cz.cvut.fit.tjv.movie_rentals.utilities.EntityStateException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Iterable<Customer> getCustomers() {
        return customerService.readAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable long id) {
        Optional<Customer> optionalCustomer = customerService.readById(id);
        if (optionalCustomer.isPresent()) return optionalCustomer.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{forename}/{email}")
    public Customer getCustomerByForenameAndEmail(@PathVariable String forename, @PathVariable String email) {
        Optional<Customer> optionalCustomer = customerService.readByForenameAndEmail(forename, email);
        if (optionalCustomer.isPresent()) return optionalCustomer.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @Operation(description = "Create a new customer")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "406", description = "Customer must be created without any rented movies AND/OR Invalid Email Format.", content = @Content),
            @ApiResponse(responseCode = "409", description = "duplicate ID.", content = @Content)
    })
    public Customer create(@RequestBody Customer data) {
        try {
            customerService.isValid(data);
            return customerService.create(data);
        } catch (CustomerMovieException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        try {
            customerService.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(description = "Update the description of an existing customer")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Invalid id, entity not found", content = @Content),
            @ApiResponse(responseCode = "406", description = "Invalid Email Format / Can not alter a customers rented movies outside rent movie functionality", content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@PathVariable long id, @RequestBody Customer data) {
        try {
            customerService.isValid(data);
            customerService.update(id, data);
        } catch (CustomerMovieException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
