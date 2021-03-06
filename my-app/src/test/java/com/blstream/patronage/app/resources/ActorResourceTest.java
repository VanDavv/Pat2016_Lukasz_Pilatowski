package com.blstream.patronage.app.resources;

import com.blstream.patronage.app.exceptions.DataAccessException;
import com.google.common.base.Optional;
import com.blstream.patronage.app.db.ActorDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import com.blstream.patronage.app.model.Actor;
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
    public void getActor() throws DataAccessException {
        when(DAO.findById(1L)).thenReturn(actor);

        final Response response = RULE.getJerseyTest().target("/actors/1").request().get();

        assertThat(response.readEntity(Actor.class)).isEqualToComparingFieldByField(actor);
    }

    @Test
    public void getActorNotFound() throws DataAccessException {
        when(DAO.findById(2L)).thenThrow(new DataAccessException("Test error"));
        final Response response = RULE.getJerseyTest().target("/actors/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void deleteActor() throws DataAccessException {
        doNothing().when(DAO).delete(1L);
        final Response response = RULE.getJerseyTest().target("/actors/1").request().delete();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void deleteNotFoundActor() throws DataAccessException {
        doThrow(new DataAccessException("Test error")).when(DAO).delete(2L);
        final Response response = RULE.getJerseyTest().target("/actors/2").request().delete();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }
    @Test
     public void updateActor() throws DataAccessException {
        when(DAO.findById(1L)).thenReturn(actor);
        when(DAO.update(any(Actor.class))).thenReturn(actor);
        final Response response = RULE.getJerseyTest().target("/actors/1").request().put(
                Entity.json(actor));

        assertThat(response.readEntity(Actor.class)).isEqualToComparingFieldByField(actor);
    }

    @Test
    public void updateNotFoundActor() throws DataAccessException {
        when(DAO.findById(2L)).thenThrow(new DataAccessException("Test error"));
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
