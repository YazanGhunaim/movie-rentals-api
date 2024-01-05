package cz.cvut.fit.tjv.movie_rentals.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

/*
Movie class: Describes Movie entity
             a movie has an ID, title, release date and an availability status
*/

@Entity
public class Movie implements EntityWithId<Long> {
    @Id
    @GeneratedValue
    private Long movieID;
    private String title;
    private Date releaseDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Genre genre;

    private boolean available;

    // constructor
    public Movie(Long movieID, String title, Date releaseDate, Genre genre, boolean available) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.available = available;
    }

    // no-arg constructor for data jpa
    public Movie() {
    }

    // Getters & Setters
    // EntityWithId interface override
    @Override
    public Long getID() {
        return this.movieID;
    }

    public Long getMovieID() {
        return movieID;
    }

    public void setMovieID(Long movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return available == movie.available && Objects.equals(movieID, movie.movieID) && Objects.equals(title, movie.title) && Objects.equals(releaseDate, movie.releaseDate) && Objects.equals(genre, movie.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieID, title, releaseDate, genre, available);
    }
}
