package cz.cvut.fit.tjv.movie_rentals.repositories;

import cz.cvut.fit.tjv.movie_rentals.domain.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {
}
