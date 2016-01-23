package resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import db.MovieDAO;
import io.dropwizard.hibernate.UnitOfWork;
import models.Movie;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.List;

@Path("/movies")
@Produces("application/json")
public class MovieResource {
    private final MovieDAO movieDao;

    public MovieResource(MovieDAO movieDao) {
        this.movieDao = movieDao;
    }

    @POST
    @UnitOfWork
    public Movie postMovie(@Valid Movie movie) {
        return movieDao.create(movie);
    }

    @PUT
    @UnitOfWork
    @Path("/{movieId}")
    public Movie updateMovie(@Valid Movie newMovie, @PathParam("movieId") long id) {
        Optional<Movie> optionalMovie = movieDao.findById(id);
        if(!optionalMovie.isPresent())
            throw new NotFoundException("No such movie");

        return movieDao.update(newMovie);
    }

    @DELETE
    @UnitOfWork
    @Path("/{movieId}")
    public void deleteMovie(@PathParam("movieId") long id) {
        Optional<Movie> movieOptional = movieDao.delete(id);
        if(!movieOptional.isPresent()) {
            throw new NotFoundException("No such  movie.");
        }
        return;
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
    public Movie getMovie(@PathParam("movieId") long id) {
        Optional<Movie> movieOptional = movieDao.findById(id);
        if(!movieOptional.isPresent()) {
            throw new NotFoundException("No such  movie.");
        }
        return movieOptional.get();
    }
}