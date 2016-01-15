package resources;

import api.Saying;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import db.ActorDAO;
import io.dropwizard.hibernate.UnitOfWork;
import models.Actor;

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
    public Saying postActor(Actor actor) {
        dao.create(actor);
        return new Saying("Added : " + actor.toString());
    }

    @PUT
    @UnitOfWork
    @Path("/{actorId}")
    public Saying updateActor(Actor newActor, @PathParam("actorId") long id) {
        Optional<Actor> actorOptional = dao.findById(id);
        if(!actorOptional.isPresent()) {
            throw new NotFoundException("No such  actor.");
        }
        Actor actor = actorOptional.get();
            actor.setId(newActor.getId());
            actor.setBirthDate(newActor.getBirthDate());
            actor.setMovie(newActor.getMovie());
            actor.setName(newActor.getName());

        dao.update(actor);
        return new Saying("Updated : " + actor.toString());
    }

    @DELETE
    @UnitOfWork
    @Path("/{actorId}")
    public Saying deleteActor(@PathParam("actorId") long id) {
        Optional<Actor> actorOptional = dao.findById(id);
        if(!actorOptional.isPresent()) {
            throw new NotFoundException("No such  actor.");
        }
        dao.delete(id);
        return new Saying("Deleted actor with id " + id);
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
    public Saying getActor(@PathParam("actorId") long id) {
        Optional<Actor> actorOptional = dao.findById(id);
        if(!actorOptional.isPresent()) {
            throw new NotFoundException("No such  actor.");
        }
        Actor actor = actorOptional.get();
        return new Saying(actor.toString());
    }
}
