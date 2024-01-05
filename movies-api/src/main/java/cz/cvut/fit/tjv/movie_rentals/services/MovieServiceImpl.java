package cz.cvut.fit.tjv.movie_rentals.services;

import cz.cvut.fit.tjv.movie_rentals.domain.Customer;
import cz.cvut.fit.tjv.movie_rentals.domain.Genre;
import cz.cvut.fit.tjv.movie_rentals.domain.Movie;
import cz.cvut.fit.tjv.movie_rentals.repositories.CustomerRepository;
import cz.cvut.fit.tjv.movie_rentals.repositories.GenreRepository;
import cz.cvut.fit.tjv.movie_rentals.repositories.MovieRepository;
import cz.cvut.fit.tjv.movie_rentals.utilities.EntityStateException;
import cz.cvut.fit.tjv.movie_rentals.utilities.InvalidGenreException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class MovieServiceImpl extends CrudServiceImpl<MovieRepository, Movie, Long> implements MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    public MovieServiceImpl(MovieRepository movieRepository, GenreRepository genreRepository, CustomerRepository customerRepository, CustomerService customerService) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @Override
    public void update(Long id, Movie entity) {
        if (id == null || entity == null)
            throw new IllegalArgumentException("Invalid entity/id");

        Optional<Movie> existingEntityOptional = getRepository().findById(id);
        if (existingEntityOptional.isPresent()) {
            Movie existingEntity = existingEntityOptional.get();
            Genre existingGenre = existingEntity.getGenre();

            existingEntity.setTitle(entity.getTitle());
            existingEntity.setReleaseDate(entity.getReleaseDate());
            existingEntity.setAvailable(entity.isAvailable());

            existingGenre.setMovieGenre(entity.getGenre().getMovieGenre());

            existingEntity.setGenre(existingGenre);

            genreRepository.save(existingGenre);
            movieRepository.save(existingEntity);
        } else {
            throw new EntityNotFoundException("Could not find entity with ID: " + id);
        }
    }

    @Override
    public void rentMovie(Long customerId, Long movieId) {
        if (customerId == null || movieId == null)
            throw new IllegalArgumentException("Invalid id's");

        Optional<Customer> optionalCustomer = customerService.readById(customerId);
        if (optionalCustomer.isEmpty())
            throw new EntityStateException();

        Customer customer = optionalCustomer.get();

        // check if id corresponds to actual movie
        Collection<Movie> rentedMovies = customer.getRentedMovies();
        Optional<Movie> optionalMovie = readById(movieId);

        if (optionalMovie.isEmpty())
            throw new EntityStateException();

        Movie movie = optionalMovie.get();
        if (movie.isAvailable())
            rentedMovies.add(movie);


        if (rentedMovies.isEmpty())
            throw new EntityStateException();

        customer.setRentedMovies(rentedMovies);
        customerRepository.save(customer);
    }

    @Override
    public Collection<Movie> readAllByGenreAndAvailability(String genre, boolean availability) {
        return movieRepository.findAllByGenreAndAvailable(genre, availability);
    }

    @Override
    public Collection<Movie> readAllByGenre(String genre) {
        return movieRepository.findAllByGenre(genre);
    }

    @Override
    public Collection<Movie> readAllByAvailability(boolean availability) {
        return movieRepository.findAllByAvailable(availability);
    }

    @Override
    public void isValid(Movie movie) {
        List<String> validGenres = List.of("horror", "comedy", "thriller", "action");
        String givenGenre = movie.getGenre().getMovieGenre().toLowerCase();
        if (!validGenres.contains(givenGenre))
            throw new InvalidGenreException();
    }

    @Override
    protected MovieRepository getRepository() {
        return movieRepository;
    }

}
