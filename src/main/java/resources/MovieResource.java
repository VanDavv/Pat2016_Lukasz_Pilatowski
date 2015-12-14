package resources;

import api.Saying;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import db.ActorDAO;
import db.MovieDAO;
import io.dropwizard.hibernate.UnitOfWork;
import models.Actor;
import models.Movie;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VanDavv on 2015-12-13.
 */

@Path("/movies")
@Produces("application/json")
public class MovieResource {
    private final MovieDAO movieDao;
    private final ActorDAO actorDAO;

    public MovieResource(MovieDAO movieDao, ActorDAO actorDAO) {
        this.movieDao = movieDao;
        this.actorDAO = actorDAO;
    }

    private List<Actor> getActorsByIds(List<Long> ids) {
        List<Actor> actors = new ArrayList<>();
        for(long id : ids) {
            Optional<Actor> actor = actorDAO.findById(id);
            if(!actor.isPresent()) continue;

            actors.add(actor.get());
        }
        return actors;
    }

    @POST
    @UnitOfWork
    public Saying postMovie(@HeaderParam("movieName") String movieName,
                            @HeaderParam("actors") List<Long> paramIds) {
        Optional<List<Long>> ids = Optional.fromNullable(paramIds);
        if(!ids.isPresent()) {
            Movie movie = movieDao.create(new Movie(movieName, null));
            return new Saying("Added : " + movie.toString());
        }

        Movie movie = movieDao.create(new Movie(movieName, getActorsByIds(ids.get())));
        return new Saying("Added : " + movie.toString());
    }

    @PUT
    @UnitOfWork
    @Path("/{movieId}")
    public Saying updateMovie(@HeaderParam("movieName") Optional<String> movieName,
                              @HeaderParam("actors") List<Long> paramIds,
                              @PathParam("movieId") long id) {
        Optional<List<Long>> ids = Optional.fromNullable(paramIds);

        Optional<Movie> optionalMovie = movieDao.findById(id);
        if(!optionalMovie.isPresent())
            throw new NotFoundException("No such movie");
        Movie movie = optionalMovie.get();

        movie.setMovieName(movieName.or(movie.getMovieName()));
        if(ids.isPresent()) movie.setActors(getActorsByIds(ids.get()));

        movieDao.update(movie);
        return new Saying("Updated : " + movie.toString());
    }

    @DELETE
    @UnitOfWork
    @Path("/{movieId}")
    public Saying deleteMovie(@PathParam("movieId") long id) {
        Optional<Movie> movieOptional = movieDao.findById(id);
        if(!movieOptional.isPresent()) {
            throw new NotFoundException("No such  movie.");
        }
        movieDao.delete(id);
        return new Saying("Deleted movie with id " + id);
    }
    @GET
    @Timed
    @UnitOfWork
    public List<Movie> getMovies() {
        return movieDao.findAll();
    }

    @Path("/{movieId}")
    @GET
    @Timed
    @UnitOfWork
    public Saying getMovie(@PathParam("movieId") long id) {
        Optional<Movie> movieOptional = movieDao.findById(id);
        if(!movieOptional.isPresent()) {
            throw new NotFoundException("No such  movie.");
        }
        Movie movie = movieOptional.get();
        return new Saying(movie.toString());
    }
}
