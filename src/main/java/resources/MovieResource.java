package resources;

import api.Saying;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import db.MovieDAO;
import io.dropwizard.hibernate.UnitOfWork;
import models.Actor;
import models.Movie;

import javax.ws.rs.*;
import java.util.List;

@Path("/movies")
@Produces("application/json")
public class MovieResource {
    private final MovieDAO movieDao;

    public MovieResource(MovieDAO movieDao) {
        this.movieDao = movieDao;
    }

//    public static class RequestBody {
//        public RequestBody() {
//        }
//
//        public RequestBody(String movieName, List<Actor> actors) {
//            this.movieName = movieName;
//            this.actors = actors;
//        }
//
//        @JsonProperty("movieName")
//        private String movieName;
//
//        @JsonProperty("actors")
//        private List<Actor> actors;
//
//
//        public String getMovieName() {
//            return movieName;
//        }
//
//        public void setMovieName(String movieName) {
//            this.movieName = movieName;
//        }
//
//        public List<Actor> getActors() {
//            return actors;
//        }
//
//        public void setActors(List<Actor> actors) {
//            this.actors = actors;
//        }
//    }

    @POST
    @UnitOfWork
    public Saying postMovie(Movie movie) {
        movieDao.create(movie);
        return new Saying("Added : " + movie.toString());
    }

    @PUT
    @UnitOfWork
    @Path("/{movieId}")
    public Saying updateMovie(Movie newMovie, @PathParam("movieId") long id) {
        Optional<Movie> optionalMovie = movieDao.findById(id);
        if(!optionalMovie.isPresent())
            throw new NotFoundException("No such movie");
        Movie movie = optionalMovie.get();

        movie.setMovieName(newMovie.getMovieName());
        movie.setActors(newMovie.getActors());

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
