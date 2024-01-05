package cz.cvut.fit.tjv.movie_rentals.services;

import cz.cvut.fit.tjv.movie_rentals.domain.Genre;


public interface GenreService extends CrudService<Genre, Long> {
    void isValid(Genre genre);
}
