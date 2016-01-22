package resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import db.ActorDAO;
import io.dropwizard.hibernate.UnitOfWork;
import models.Actor;

import javax.validation.Valid;
import javax.ws.rs.*;
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
        Optional<Actor> actorOptional = dao.findById(id);
        if(!actorOptional.isPresent()) {
            throw new NotFoundException("No such  actor.");
        }
        return dao.update(newActor);
    }

    @DELETE
    @UnitOfWork
    @Path("/{actorId}")
    public void deleteActor(@PathParam("actorId") long id) {
        Optional<Actor> actorOptional = dao.delete(id);
        if(!actorOptional.isPresent()) {
            throw new NotFoundException("No such  actor.");
        }
        return;
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
        Optional<Actor> actorOptional = dao.findById(id);
        if(!actorOptional.isPresent()) {
            throw new NotFoundException("No such  actor.");
        }
        return actorOptional.get();
    }
}
