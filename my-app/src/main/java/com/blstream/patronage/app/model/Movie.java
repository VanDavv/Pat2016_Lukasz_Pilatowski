package com.blstream.patronage.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Movies")
public class Movie {
    private long id;

    @JsonProperty
    @NotNull
    private String movieName;
    @JsonProperty
    private List<Actor> actors;

    public Movie() {
    }

    public Movie(String movieName, List<Actor> actors) {
        this.movieName = movieName;
        this.actors = actors;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieId")
    public long getId() {
        return id;
    }

    @Column(name = "movieName", nullable = false)
    public String getMovieName() {
        return movieName;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "movie")
    public List<Actor> getActors() {
        return actors;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (movieName != null ? !movieName.equals(movie.movieName) : movie.movieName != null) return false;
        return !(actors != null ? !actors.equals(movie.actors) : movie.actors != null);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieName, actors);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", actors=" + actors +
                '}';
    }
}
