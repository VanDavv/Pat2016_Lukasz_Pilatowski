package resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import db.MovieDAO;
import io.dropwizard.hibernate.UnitOfWork;
import models.Movie;
import services.UpdateService;

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
    public Movie postMovie(Movie movie) {
        return movieDao.create(movie);
    }

    @PUT
    @UnitOfWork
    @Path("/{movieId}")
    public Movie updateMovie(Movie newMovie, @PathParam("movieId") long id) {
        Optional<Movie> optionalMovie = movieDao.findById(id);
        if(!optionalMovie.isPresent())
            throw new NotFoundException("No such movie");

        Movie movie = new UpdateService().updateMovie(optionalMovie.get(),newMovie);
        return movieDao.update(movie);
    }

    @DELETE
    @UnitOfWork
    @Path("/{movieId}")
    public Movie deleteMovie(@PathParam("movieId") long id) {
        Optional<Movie> movieOptional = movieDao.delete(id);
        if(!movieOptional.isPresent()) {
            throw new NotFoundException("No such  movie.");
        }
        return movieOptional.get();
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
