package cz.cvut.fit.tjv.movie_rentals.controllers;

import cz.cvut.fit.tjv.movie_rentals.domain.Genre;
import cz.cvut.fit.tjv.movie_rentals.services.GenreService;
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
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public Iterable<Genre> getGenres() {
        return genreService.readAll();
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable long id) {
        Optional<Genre> optionalGenre = genreService.readById(id);
        if (optionalGenre.isPresent()) return optionalGenre.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @Operation(description = "Create a new genre")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "406", description = "Invalid Movie Genre, Valid genres : Action, Comedy, Horror, Thriller", content = @Content),
            @ApiResponse(responseCode = "409", description = "duplicate ID", content = @Content)
    })
    public Genre create(@RequestBody Genre data) {
        try {
            genreService.isValid(data);
            return genreService.create(data);
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
            genreService.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (referencedEntityException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/{id}")
    @Operation(description = "Update the description of an existing genre")
    @ApiResponses({
            @ApiResponse(responseCode = "406", description = "Invalid Movie Genre", content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@PathVariable long id, @RequestBody Genre data) {
        try {
            genreService.isValid(data);
            genreService.update(id, data);
        } catch (InvalidGenreException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
