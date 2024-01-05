package cz.cvut.fit.tjv.movie_rentals.controllers;

import cz.cvut.fit.tjv.movie_rentals.domain.Movie;
import cz.cvut.fit.tjv.movie_rentals.services.MovieService;
import cz.cvut.fit.tjv.movie_rentals.utilities.EntityStateException;
import cz.cvut.fit.tjv.movie_rentals.utilities.InvalidGenreException;
import cz.cvut.fit.tjv.movie_rentals.utilities.referencedEntityException;
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
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    @Operation(description = "Create a new movie")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "406", description = "Invalid genre", content = @Content),
            @ApiResponse(responseCode = "409", description = "duplicate ID", content = @Content)
    })
    public Movie create(@RequestBody Movie data) {
        try {
            movieService.isValid(data);
            return movieService.create(data);
        } catch (InvalidGenreException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        try {
            movieService.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (referencedEntityException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/{id}")
    @Operation(description = "Update the contents of an existing movie")
    @ApiResponses({
            @ApiResponse(responseCode = "406", description = "Invalid genre", content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@PathVariable long id, @RequestBody Movie data) {
        try {
            movieService.isValid(data);
            movieService.update(id, data);
        } catch (InvalidGenreException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    // Complex Query
    @GetMapping
    public Iterable<Movie> getAllOrByCategory(@RequestParam Optional<String> genre,
                                              @RequestParam Optional<Boolean> availability) {
        if (genre.isEmpty() && availability.isEmpty())
            return movieService.readAll();
        else if (genre.isPresent() && availability.isEmpty())
            return movieService.readAllByGenre(genre.get());
        else if (genre.isEmpty() && availability.isPresent())
            return movieService.readAllByAvailability(availability.get());
        else
            return movieService.readAllByGenreAndAvailability(genre.get(), availability.get());
    }

    @PutMapping("/{customerId}/{movieId}")
    @Operation(description = "Select one movie to rent.")
    public void rentMovie(@PathVariable Long customerId, @PathVariable Long movieId) {
        try {
            movieService.rentMovie(customerId, movieId);
        } catch (EntityStateException e) {
            System.out.println("controller");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
