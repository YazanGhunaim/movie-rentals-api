package cz.cvut.fit.tjv.movie_rentals.controllers;

import cz.cvut.fit.tjv.movie_rentals.domain.Genre;
import cz.cvut.fit.tjv.movie_rentals.services.GenreService;
import cz.cvut.fit.tjv.movie_rentals.utilities.EntityStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
class GenreControllerUnitTest {
    Genre genreWithId;
    Genre genreWithoutId;
    Genre genreWithInvalidGenre;

    @MockBean
    private GenreService genreService;
    @Autowired
    private GenreController genreController;

    @BeforeEach
    void setup() {
        genreWithoutId = new Genre();
        genreWithoutId.setMovieGenre("horror");

        genreWithId = new Genre();
        genreWithId.setMovieGenre(genreWithoutId.getMovieGenre());
        genreWithId.setGenreID(1L);

        genreWithInvalidGenre = new Genre();
        genreWithInvalidGenre.setMovieGenre("hmm");
        genreWithInvalidGenre.setGenreID(2L);

        Mockito.when(genreService.create(genreWithId)).thenReturn(genreWithId);
        Mockito.when(genreService.create(genreWithoutId)).thenReturn(genreWithId);
    }

    @Test
    void createDuplicate() {
        Mockito.when(genreService.create(genreWithId)).thenThrow(EntityStateException.class);

        Assertions.assertThrows(
                ResponseStatusException.class,
                () -> genreController.create(genreWithId)
        );
    }

    @Test
    void createWithoutId() {
        var res = genreController.create(genreWithoutId);
        Assertions.assertEquals(genreWithId, res);
        Mockito.verify(genreService, Mockito.atLeastOnce()).create(genreWithoutId);
    }

    @Test
    void createWithInvalidGenre() {
        Mockito.when(genreService.create(genreWithInvalidGenre)).thenThrow(EntityStateException.class);

        Assertions.assertThrows(
                ResponseStatusException.class,
                () -> genreController.create(genreWithInvalidGenre)
        );
    }

    @Test
    void create() {
        // verify return value
        var res = genreController.create(genreWithId);
        Assertions.assertEquals(genreWithId, res);

        // verify interactions
        Mockito.verify(genreService, Mockito.atLeastOnce()).create(genreWithId);
        Mockito.verify(genreService, Mockito.atLeastOnce()).isValid(genreWithId);
    }
}