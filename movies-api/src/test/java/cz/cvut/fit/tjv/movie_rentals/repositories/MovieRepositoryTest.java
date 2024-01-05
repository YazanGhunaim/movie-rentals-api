package cz.cvut.fit.tjv.movie_rentals.repositories;

import cz.cvut.fit.tjv.movie_rentals.domain.Genre;
import cz.cvut.fit.tjv.movie_rentals.domain.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;


@DataJpaTest
class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void findAllByGenre() {
        var m1 = new Movie();
        var m2 = new Movie();
        var m3 = new Movie();

        var g1 = new Genre();
        var g2 = new Genre();
        var g3 = new Genre();

        g1.setMovieGenre("horror");
        g2.setMovieGenre("horror");
        g3.setMovieGenre("comedy");

        m1.setMovieID(1L);
        m2.setMovieID(2L);
        m3.setMovieID(3L);

        m1.setGenre(g1);
        m2.setGenre(g2);
        m3.setGenre(g3);

        genreRepository.saveAll(List.of(g1, g2, g3));
        movieRepository.saveAll(List.of(m1, m2, m3));

        var res = movieRepository.findAllByGenre(g1.getMovieGenre());
        Assertions.assertIterableEquals(List.of(m1, m2), res);
    }
}