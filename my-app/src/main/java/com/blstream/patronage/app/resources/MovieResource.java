package com.blstream.patronage.app.resources;

import com.blstream.patronage.app.db.MovieDAO;
import com.blstream.patronage.app.exceptions.DataAccessException;
import com.blstream.patronage.app.model.Movie;
import com.blstream.patronage.app.views.MovieView;
import com.blstream.patronage.movieDataBundle.MovieProvider;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.Collections;
import java.util.List;

@Path("/movies")
@Consumes("application/json")
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
        movie.setDetailedMovieData(movieProvider.getMovieData(movie.getMovieName()));
        return movieDao.create(movie);
    }

    @PUT
    @UnitOfWork
    @Path("/{movieId}")
    public Movie updateMovie(@Valid Movie newMovie, @PathParam("movieId") LongParam movieId) {
        Movie movie;
        try {
            movieDao.findById(movieId.get());
            newMovie.setId(movieId.get());
            movie = movieDao.update(newMovie);
        } catch (DataAccessException e) {
            throw new NotFoundException(e.getMessage());
        }

        return movie;
    }

    @DELETE
    @UnitOfWork
    @Path("/{movieId}")
    public void deleteMovie(@PathParam("movieId") long id) {
        try {
            movieDao.delete(id);
        } catch (DataAccessException e) {
            throw new NotFoundException(e.getMessage());
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
        Movie movie;
        try {
            movie = movieDao.findById(id);
        } catch (DataAccessException e) {
            throw new NotFoundException(e.getMessage());
        }
        return movie;
    }

    @GET
    @Timed
    @UnitOfWork
    @Produces("text/html")
    public MovieView getMoviesHTML() {
        return new MovieView(movieDao.findAll());
    }

    @Path("/{movieId}")
    @GET
    @Timed
    @UnitOfWork
    @Produces("text/html")
    public MovieView getMovieHTML(@PathParam("movieId") long id) {
        Movie movie;
        try {
            movie = movieDao.findById(id);
        } catch (DataAccessException e) {
            throw new NotFoundException(e.getMessage());
        }
        return new MovieView(Collections.singletonList(movie));
    }
}
