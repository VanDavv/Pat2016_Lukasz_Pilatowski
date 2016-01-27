package com.blstream.patronage.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name="Actors")
@SuppressWarnings("unused")
public class Actor {
    private long id;
    @JsonProperty
    @NotNull
    private String name;
    @JsonProperty
    @NotNull
    private String birthDate;
    @JsonProperty
    private Movie movie;

    public Actor() {
    }

    public Actor(String name, String birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public Actor(String name, String birthDate, Movie movie) {
        this.name = name;
        this.birthDate = birthDate;
        this.movie = movie;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "birthDate", nullable = false)
    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }
}
