package cz.cvut.fit.tjv.movie_rentals.repositories;

import cz.cvut.fit.tjv.movie_rentals.domain.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m WHERE m.genre.movieGenre = :genre AND m.available = :isAvailable")
    Collection<Movie> findAllByGenreAndAvailable(@Param("genre") String genre, @Param("isAvailable") boolean isAvailable);

    @Query("SELECT m FROM Movie m WHERE m.genre.movieGenre = :genre")
    Collection<Movie> findAllByGenre(@Param("genre") String genre);

    Collection<Movie> findAllByAvailable(boolean isAvailable);
}
