package com.blstream.patronage.app.resources;

import com.blstream.patronage.app.db.ActorDAO;
import com.blstream.patronage.app.exceptions.DataAccessException;
import com.blstream.patronage.app.model.Actor;
import com.blstream.patronage.app.views.ActorView;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.Collections;
import java.util.List;

@Path("/actors")
@Consumes("application/json")
@Produces("application/json")
public class ActorResource {
    private final ActorDAO dao;
    public ActorResource(ActorDAO dao) {
        this.dao = dao;
    }

    @POST
    @UnitOfWork
    public Actor postActor(@Valid Actor actor) {
        return dao.create(actor);
    }

    @PUT
    @UnitOfWork
    @Path("/{actorId}")
    public Actor updateActor(@Valid Actor newActor, @PathParam("actorId") long id) {
        try {
            dao.findById(id);
            newActor.setId(id);
        } catch (DataAccessException e) {
            throw new NotFoundException(e.getMessage());
        }
        return dao.update(newActor);
    }

    @DELETE
    @UnitOfWork
    @Path("/{actorId}")
    public void deleteActor(@PathParam("actorId") long id) {
        try {
            dao.delete(id);
        } catch (DataAccessException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
    @GET
    @Timed
    @UnitOfWork
    public List<Actor> getActors() {
        return dao.findAll();
    }


    @GET
    @Timed
    @UnitOfWork
    @Path("/{actorId}")
    public Actor getActor(@PathParam("actorId") long id) {
        Actor actor;
        try {
            actor = dao.findById(id);
        } catch (DataAccessException e) {
            throw new NotFoundException(e.getMessage());
        }
        return actor;
    }

    @GET
    @Timed
    @UnitOfWork
    @Produces("text/html")
    public ActorView getActorsHTML() {
        return new ActorView(dao.findAll());
    }


    @GET
    @Timed
    @UnitOfWork
    @Path("/{actorId}")
    @Produces("text/html")
    public ActorView getActorHTML(@PathParam("actorId") long id) {
        Actor actor;
        try {
            actor = dao.findById(id);
        } catch (DataAccessException e) {
            throw new NotFoundException(e.getMessage());
        }
        return new ActorView(Collections.singletonList(actor));
    }
}
