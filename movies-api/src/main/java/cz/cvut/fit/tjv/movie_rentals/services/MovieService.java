package cz.cvut.fit.tjv.movie_rentals.services;

import cz.cvut.fit.tjv.movie_rentals.domain.Movie;

import java.util.Collection;

public interface MovieService extends CrudService<Movie, Long> {
    void rentMovie(Long customerId, Long movieIds);

    Collection<Movie> readAllByGenreAndAvailability(String genre, boolean availability);

    Collection<Movie> readAllByGenre(String genre);

    Collection<Movie> readAllByAvailability(boolean availability);

    void isValid(Movie movie);
}
