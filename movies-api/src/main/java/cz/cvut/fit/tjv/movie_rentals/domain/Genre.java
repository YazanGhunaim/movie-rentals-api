package cz.cvut.fit.tjv.movie_rentals.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Genre implements EntityWithId<Long> {
    @Id
    @GeneratedValue
    private Long genreID;

    private String movieGenre;

    // Constructor
    public Genre(Long genreID, String movieGenre) {
        this.genreID = genreID;
        this.movieGenre = movieGenre;
    }

    public Genre(String genre) {
        this.movieGenre = genre;
    }

    // no-arg constructor for data jpa
    public Genre() {
    }

    // Getters and Setters

    // EntityWithId interface override
    @Override
    public Long getID() {
        return genreID;
    }

    public Long getGenreID() {
        return genreID;
    }

    public void setGenreID(Long genreID) {
        this.genreID = genreID;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String genre) {
        this.movieGenre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(genreID, genre.genreID) && Objects.equals(movieGenre, genre.movieGenre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreID, movieGenre);
    }
}
