package models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by VanDavv on 2015-12-13.
 */
@Entity
@Table(name = "Movies")
@NamedQueries({
        @NamedQuery(
                name = "models.Movie.findAll",
                query = "Select m from Movies m"
        )
})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "movieName")
    private String movieName;

    @ElementCollection
    private List<Actor> actors;

    public Movie() {
    }

    public Movie(String movieName, List<Actor> actors) {
        this.movieName = movieName;
        this.actors = actors;
    }

    public long getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
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
