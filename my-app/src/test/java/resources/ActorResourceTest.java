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

public class ActorResourceTest {
    private static final ActorDAO DAO = mock(ActorDAO.class);
    @ClassRule
    public static final ResourceTestRule RULE = ResourceTestRule.builder()
            .addResource(new ActorResource(DAO))
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .build();
    private Actor actor;
    private Actor incompleteActor;

    @Before
    public void setup() {
        actor = new Actor();
        actor.setId(0L);
        actor.setBirthDate("12-03-2014");
        actor.setName("Karol Marks");

        incompleteActor = new Actor();
        incompleteActor.setId(0L);
        incompleteActor.setName(null);
        incompleteActor.setBirthDate(null);
    }

    @After
    public void tearDown() {
        reset(DAO);
    }

    @Test
    public void getActor() {
        when(DAO.findById(1L)).thenReturn(Optional.of(actor));

        final Response response = RULE.getJerseyTest().target("/actors/1").request().get();

        assertThat(response.readEntity(Actor.class)).isEqualToComparingFieldByField(actor);
    }

    @Test
    public void getActorNotFound() {
        when(DAO.findById(2L)).thenReturn(Optional.<Actor>absent());
        final Response response = RULE.getJerseyTest().target("/actors/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void deleteActor() {
        when(DAO.delete(1L)).thenReturn(Optional.of(actor));
        final Response response = RULE.getJerseyTest().target("/actors/1").request().delete();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void deleteNotFoundActor() {
        when(DAO.delete(2L)).thenReturn(Optional.<Actor>absent());
        final Response response = RULE.getJerseyTest().target("/actors/2").request().delete();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }
    @Test
     public void updateActor() {
        when(DAO.findById(1L)).thenReturn(Optional.of(actor));
        when(DAO.update(actor)).thenReturn(actor);
        final Response response = RULE.getJerseyTest().target("/actors/1").request().put(
                Entity.json(actor));

        assertThat(response.readEntity(Actor.class)).isEqualToComparingFieldByField(actor);
    }

    @Test
    public void updateNotFoundActor() {
        when(DAO.findById(2L)).thenReturn(Optional.<Actor>absent());
        final Response response = RULE.getJerseyTest().target("/actors/2").request().put(
                Entity.json(actor));

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }
    @Test
    public void getActors() {
        when(DAO.findAll()).thenReturn(Arrays.asList(actor));
        final Response response = RULE.getJerseyTest().target("/actors").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
    }
    @Test
    public void postActor() {
        when(DAO.create(actor)).thenReturn(actor);
        final Response response = RULE.getJerseyTest().target("/actors").request().post(
                Entity.json(actor)
        );

        assertThat(response.readEntity(Actor.class)).isEqualToComparingFieldByField(actor);
    }
    @Test
    public void postIncompleteActor() {
        final Response response = RULE.getJerseyTest().target("/actors").request().post(
                Entity.json(incompleteActor)
        );
        assertThat(response.getStatus()).isEqualTo(422);
    }
    @Test
    public void updateIncompleteActor() {
        final Response response = RULE.getJerseyTest().target("/actors/1").request().put(
                Entity.json(incompleteActor)
        );
        assertThat(response.getStatus()).isEqualTo(422);
    }
}
