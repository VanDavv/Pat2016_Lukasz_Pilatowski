package com.blstream.patronage.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Actor actor = (Actor) o;

        if (id != actor.id) return false;
        if (!name.equals(actor.name)) return false;
        if (birthDate != null ? !birthDate.equals(actor.birthDate) : actor.birthDate != null) return false;
        return !(movie != null ? !movie.equals(actor.movie) : actor.movie != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (movie != null ? movie.hashCode() : 0);
        return result;
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
