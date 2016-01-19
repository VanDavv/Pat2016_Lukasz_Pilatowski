package services;

import models.Actor;
import models.Movie;

/**
 * Created by VanDavv on 2016-01-19.
 */
public class UpdateService {
    public Actor updateActor(Actor oldActor, Actor newActor) {
        oldActor.setBirthDate(newActor.getBirthDate());
        oldActor.setId(newActor.getId());
        oldActor.setMovie(newActor.getMovie());
        oldActor.setName(newActor.getName());

        return oldActor;
    }
    public Movie updateMovie(Movie oldMovie, Movie newMovie) {
        oldMovie.setId(newMovie.getId());
        oldMovie.setActors(newMovie.getActors());
        oldMovie.setMovieName(newMovie.getMovieName());

        return oldMovie;
    }
}
