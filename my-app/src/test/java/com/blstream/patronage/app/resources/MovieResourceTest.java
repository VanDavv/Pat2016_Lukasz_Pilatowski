package com.blstream.patronage.app.resources;

import com.blstream.patronage.movieDataBundle.MovieProvider;
import com.google.common.base.Optional;
import com.blstream.patronage.app.db.MovieDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import com.blstream.patronage.app.model.Actor;
import com.blstream.patronage.app.model.Movie;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by 304-03 on 2015-12-14.
 */
public class MovieResourceTest {

    private static final MovieDAO DAO = mock(MovieDAO.class);
    private static final MovieProvider MOVIE_PROVIDER = mock(MovieProvider.class);

    @ClassRule
    public static final ResourceTestRule RULE = ResourceTestRule.builder()
            .addResource(new MovieResource(DAO, MOVIE_PROVIDER))
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .build();
    private Movie movie;
    private Movie incompleteMovie;

    @Before
    public void setup() {
        movie = new Movie();
        movie.setId(0L);
        movie.setMovieName("TestName");
        movie.setActors(Arrays.asList(new Actor("John","13")));

        incompleteMovie = new Movie();
        incompleteMovie.setId(0L);
        incompleteMovie.setMovieName(null);
    }

    @After
    public void tearDown() {
        reset(DAO);
    }

    @Test
    public void getMovie() {
        when(DAO.findById(1L)).thenReturn(Optional.of(movie));

        final Response response = RULE.getJerseyTest().target("/movies/1").request().get();

        assertThat(response.readEntity(Movie.class)).isEqualToComparingFieldByField(movie);
    }

    @Test
    public void getMovieNotFound() {
        when(DAO.findById(2L)).thenReturn(Optional.<Movie>absent());
        final Response response = RULE.getJerseyTest().target("/movies/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void deleteMovie() {
        when(DAO.delete(1L)).thenReturn(Optional.of(movie));
        final Response response = RULE.getJerseyTest().target("/movies/1").request().delete();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void deleteNotFoundMovie() {
        when(DAO.delete(2L)).thenReturn(Optional.<Movie>absent());
        final Response response = RULE.getJerseyTest().target("/movies/2").request().delete();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }
    @Test
     public void updateMovie() {
        when(DAO.findById(1L)).thenReturn(Optional.of(movie));
        when(DAO.update(movie)).thenReturn(movie);
        final Response response = RULE.getJerseyTest().target("/movies/1").request().put(Entity.json(movie));

        assertThat(response.readEntity(Movie.class)).isEqualToComparingFieldByField(movie);
    }

    @Test
    public void updateNotFoundMovie() {
        when(DAO.findById(2L)).thenReturn(Optional.<Movie>absent());
        final Response response = RULE.getJerseyTest().target("/movies/2").request().put(Entity.json(movie));

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }
    @Test
    public void getMovies() {
        when(DAO.findAll()).thenReturn(Arrays.asList(movie));
        final Response response = RULE.getJerseyTest().target("/movies").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
    }
    @Test
    public void postMovie() {
        when(DAO.create(movie)).thenReturn(movie);
        final Response response = RULE.getJerseyTest().target("/movies").request().post(Entity.json(movie));

        assertThat(response.readEntity(Movie.class)).isEqualToComparingFieldByField(movie);
    }
    @Test
    public void postIncompleteMovie() {
        final Response response = RULE.getJerseyTest().target("/movies").request().post(
                Entity.json(incompleteMovie)
        );
        assertThat(response.getStatus()).isEqualTo(422);
    }
    @Test
    public void updateIncompleteMovie() {
        final Response response = RULE.getJerseyTest().target("/movies/1").request().put(
                Entity.json(incompleteMovie)
        );
        assertThat(response.getStatus()).isEqualTo(422);
    }
}
