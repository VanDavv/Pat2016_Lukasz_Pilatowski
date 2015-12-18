package resources;

import com.google.common.base.Optional;
import db.ActorDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import models.Actor;
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
public class ActorResourceTest {
    private static final ActorDAO DAO = mock(ActorDAO.class);
    @ClassRule
    public static final ResourceTestRule RULE = ResourceTestRule.builder()
            .addResource(new ActorResource(DAO))
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .build();
    private Actor actor;

    @Before
    public void setup() {
        actor = new Actor();
        actor.setId(0L);
        actor.setBirthDate("12-03-2014");
        actor.setName("Karol Marks");
    }

    @After
    public void tearDown() {
        reset(DAO);
    }

    @Test
    public void getActor() {
        when(DAO.findById(1L)).thenReturn(Optional.of(actor));

        final Response response = RULE.getJerseyTest().target("/actors/1").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        verify(DAO).findById(1L);
    }

    @Test
    public void getActorNotFound() {
        when(DAO.findById(2L)).thenReturn(Optional.<Actor>absent());
        final Response response = RULE.getJerseyTest().target("/actors/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2L);
    }

    @Test
    public void deleteActor() {
        when(DAO.findById(1L)).thenReturn(Optional.of(actor));
        final Response response = RULE.getJerseyTest().target("/actors/1").request().delete();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        verify(DAO).delete(1L);
    }

    @Test
    public void deleteNotFoundActor() {
        when(DAO.findById(2L)).thenReturn(Optional.<Actor>absent());
        final Response response = RULE.getJerseyTest().target("/actors/2").request().delete();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2L);
    }
    @Test
     public void updateActor() {
        when(DAO.findById(1L)).thenReturn(Optional.of(actor));
        final Response response = RULE.getJerseyTest().target("/actors/1").request().put(
                Entity.json(
                        new ActorResource.RequestBody(
                                actor.getName(),
                                actor.getBirthDate()
                        )
                )
        );

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        verify(DAO).update(actor);
    }

    @Test
    public void updateNotFoundActor() {
        when(DAO.findById(2L)).thenReturn(Optional.<Actor>absent());
        final Response response = RULE.getJerseyTest().target("/actors/2").request().put(
                Entity.json(
                        new ActorResource.RequestBody(
                                actor.getName(),
                                actor.getBirthDate()
                        )
                )
        );

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2L);
    }
    @Test
    public void getActors() {
        when(DAO.findAll()).thenReturn(Arrays.asList(actor));
        final Response response = RULE.getJerseyTest().target("/actors").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        verify(DAO).findAll();
    }
    @Test
    public void postActor() {
        Actor any = any(Actor.class);
        when(DAO.create(any)).thenReturn(actor);
        final Response response = RULE.getJerseyTest().target("/actors").request().post(
                Entity.json(
                        new ActorResource.RequestBody(
                                actor.getName(),
                                actor.getBirthDate()
                        )
                )
        );

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        verify(DAO).create(actor);
    }
}
