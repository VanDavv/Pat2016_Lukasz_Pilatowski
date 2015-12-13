package resources;

import api.Saying;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import db.MovieDAO;
import io.dropwizard.hibernate.UnitOfWork;
import models.Movie;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by VanDavv on 2015-12-13.
 */

@Path("/movies")
@Produces("application/json")
public class MovieResource {
    private final String template;
    private final String defaultName;
    private final MovieDAO dao;

    public MovieResource(String template, String defaultName, MovieDAO dao) {
        this.template = template;
        this.defaultName = defaultName;
        this.dao = dao;
    }

    @GET
    @Timed
    @UnitOfWork
    public List<Movie> getMovies() {
        dao.findAll();
        return dao.findAll();
    }

    @Path("/{movieId}")
    @GET
    @Timed
    @UnitOfWork
    public Saying getMovie(@PathParam("movieId") long id) {
        Optional<Movie> movieOptional = dao.findById(id);
        if(movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            return new Saying(movie.toString());
        }
        return new Saying("No such movie with id " + id);
    }
}
