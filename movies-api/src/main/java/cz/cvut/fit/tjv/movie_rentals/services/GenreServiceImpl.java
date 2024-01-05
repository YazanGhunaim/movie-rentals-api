package cz.cvut.fit.tjv.movie_rentals.services;

import cz.cvut.fit.tjv.movie_rentals.domain.Genre;
import cz.cvut.fit.tjv.movie_rentals.repositories.GenreRepository;
import cz.cvut.fit.tjv.movie_rentals.utilities.InvalidGenreException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GenreServiceImpl extends CrudServiceImpl<GenreRepository, Genre, Long> implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public void update(Long id, Genre entity) {
        if (id == null || entity == null)
            throw new IllegalArgumentException("Invalid entity/id");


        Optional<Genre> existingEntityOptional = getRepository().findById(id);
        if (existingEntityOptional.isPresent()) {
            Genre existingGenre = existingEntityOptional.get();
            existingGenre.setMovieGenre(entity.getMovieGenre());
            genreRepository.save(existingGenre);
        } else {
            throw new EntityNotFoundException("Could not find entity with ID: " + id);
        }
    }

    @Override
    public void isValid(Genre genre) {
        List<String> validGenres = List.of("horror", "comedy", "thriller", "action");
        String givenGenre = genre.getMovieGenre().toLowerCase();
        if (!validGenres.contains(givenGenre))
            throw new InvalidGenreException();
    }

    @Override
    protected GenreRepository getRepository() {
        return genreRepository;
    }
}
