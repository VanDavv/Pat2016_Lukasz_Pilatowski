package resources;

import com.google.common.base.Optional;
import db.ActorDAO;
import db.MovieDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import models.Actor;
import models.Movie;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by 304-03 on 2015-12-14.
 */
public class MovieResourceTest {
    private static final MovieDAO movieDAO = mock(MovieDAO.class);
    private static final ActorDAO actorDAO = mock(ActorDAO.class);
    @ClassRule
    public static final ResourceTestRule RULE = ResourceTestRule.builder()
            .addResource(new MovieResource(movieDAO, actorDAO))
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .build();
    private Movie movie;

    @Before
    public void setup() {
        movie = new Movie();
        movie.setId(1L);
        movie.setMovieName("TestName");
        movie.setActors(Arrays.asList(new Actor("John","13")));
    }

    @After
    public void tearDown() {
        reset(movieDAO);
    }

    @Test
    public void getMovie() {
        when(movieDAO.findById(1L)).thenReturn(Optional.of(movie));

        final Response response = RULE.getJerseyTest().target("/movies/1").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        verify(movieDAO).findById(1L);
    }

    @Test
    public void getMovieNotFound() {
        when(movieDAO.findById(2L)).thenReturn(Optional.<Movie>absent());
        final Response response = RULE.getJerseyTest().target("/movies/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(movieDAO).findById(2L);
    }

    @Test
    public void deleteMovie() {
        when(movieDAO.findById(1L)).thenReturn(Optional.of(movie));
        final Response response = RULE.getJerseyTest().target("/movies/1").request().delete();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        verify(movieDAO).delete(1L);
    }

    @Test
    public void deleteNotFoundMovie() {
        when(movieDAO.findById(2L)).thenReturn(Optional.<Movie>absent());
        final Response response = RULE.getJerseyTest().target("/movies/2").request().delete();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(movieDAO).findById(2L);
    }
    @Test
     public void updateMovie() {
        when(movieDAO.findById(1L)).thenReturn(Optional.of(movie));
        final Response response = RULE.getJerseyTest().target("/movies/1").request().put(Entity.text(""));

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        verify(movieDAO).update(movie);
    }

    @Test
    public void updateNotFoundMovie() {
        when(movieDAO.findById(2L)).thenReturn(Optional.<Movie>absent());
        final Response response = RULE.getJerseyTest().target("/movies/2").request().put(Entity.text(""));

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(movieDAO).findById(2L);
    }
    @Test
    public void getMovies() {
        when(movieDAO.findAll()).thenReturn(Arrays.asList(movie));
        final Response response = RULE.getJerseyTest().target("/movies").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        verify(movieDAO).findAll();
    }
    @Test
    public void postMovie() {
        Movie any = any(Movie.class);
        when(movieDAO.create(any)).thenReturn(movie);
        final Response response = RULE.getJerseyTest().target("/movies").request().post(Entity.text(""));

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        verify(movieDAO).create((Movie)notNull());
    }
}
