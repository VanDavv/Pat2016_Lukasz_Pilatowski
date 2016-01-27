package com.blstream.patronage.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Movies")
@SuppressWarnings("unused")
public class Movie {
    private long id;

    @JsonProperty
    @NotNull
    private String movieName;
    @JsonProperty
    private List<Actor> actors;
    private com.blstream.patronage.movieDataBundle.Movie detailedMovieData;

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
    public void setDetailedMovieData(com.blstream.patronage.movieDataBundle.Movie detailedMovieData) {
        this.detailedMovieData = detailedMovieData;
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

    @Type(type = "serializable")
    @Column(name = "movieDetailsObject")
    public com.blstream.patronage.movieDataBundle.Movie getDetailedMovieData() {
        return detailedMovieData;
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
