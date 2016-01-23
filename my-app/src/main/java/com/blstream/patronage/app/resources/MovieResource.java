package com.blstream.patronage.app.resources;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.blstream.patronage.app.db.MovieDAO;
import com.blstream.patronage.app.model.Movie;
import com.blstream.patronage.movieDataBundle.MovieProvider;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/movies")
@Produces("application/json")
public class MovieResource {

    private final MovieDAO movieDao;
    private final MovieProvider movieProvider;

    public MovieResource(final MovieDAO movieDao, final MovieProvider movieProvider) {
        this.movieDao = movieDao;
        this.movieProvider = movieProvider;
    }

    @POST
    @UnitOfWork
    public Movie postMovie(@Valid Movie movie) {
        return movieDao.create(movie);
    }

    @PUT
    @UnitOfWork
    @Path("/{movieId}")
    public Movie updateMovie(@Valid Movie newMovie, @PathParam("movieId") LongParam movieId) { // see AbstractParam class and friends
        Optional<Movie> optionalMovie = movieDao.findById(movieId.get());
        if (!optionalMovie.isPresent()) {
            // TODO
            // 1. create DataAccessException on DAO level and throw inside DAO when DB record with PK was not found
            // 2. create exception mapper for it and return NotFoundException + message from DataAccessException - keep API exception handling logic in one place
            // 3. remove all this copy & paste block: if (!present) throw new ...
            throw new NotFoundException("No such movie");
        }

        return movieDao.update(newMovie);
    }

    @DELETE
    @UnitOfWork
    @Path("/{movieId}")
    public void deleteMovie(@PathParam("movieId") long id) {
        Optional<Movie> movieOptional = movieDao.delete(id);
        if (!movieOptional.isPresent()) {
            throw new NotFoundException("No such  movie.");
        }
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
        if (!movieOptional.isPresent()) {
            throw new NotFoundException("No such  movie.");
        }
        return movieOptional.get();
    }
}
