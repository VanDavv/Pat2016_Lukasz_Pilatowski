package resources;

import api.Saying;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import db.ActorDAO;
import io.dropwizard.hibernate.UnitOfWork;
import models.Actor;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by VanDavv on 2015-12-13.
 */

@Path("/actors")
@Produces("application/json")
public class ActorResource {
    private final ActorDAO dao;

    public ActorResource(ActorDAO dao) {
        this.dao = dao;
    }

    @POST
    @UnitOfWork
    public Saying postActor(@HeaderParam("actorName") String name,@HeaderParam("actorBirthDate") String birthDate) {
        Actor actor = dao.create(new Actor(name,birthDate));
        return new Saying("Added : " + actor.toString());
    }

    @PUT
    @UnitOfWork
    @Path("/{actorId}")
    public Saying updateActor(@HeaderParam("actorName") Optional<String> name,@HeaderParam("actorBirthDate") Optional<String> birthDate, @PathParam("actorId") long id) {
        Optional<Actor> actorOptional = dao.findById(id);
        if(!actorOptional.isPresent()) {
            throw new NotFoundException("No such  actor.");
        }
        Actor actor = actorOptional.get();

        actor.setName(
                name.or(actor.getName())
        );

        actor.setBirthDate(
                birthDate.or(actor.getBirthDate())
        );

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
