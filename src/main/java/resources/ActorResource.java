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

    public static class RequestBody {
        public RequestBody() {
        }

        public RequestBody(String actorName, String actorBirthDate) {
            this.actorName = actorName;
            this.actorBirthDate = actorBirthDate;
        }

        @JsonProperty("actorName")
        public String actorName;

        @JsonProperty("actorBirthDate")
        public String actorBirthDate;
    }

    @POST
    @UnitOfWork
    public Saying postActor(RequestBody requestBody) {
        Actor actor = dao.create(new Actor(requestBody.actorName, requestBody.actorBirthDate));
        return new Saying("Added : " + actor.toString());
    }

    @PUT
    @UnitOfWork
    @Path("/{actorId}")
    public Saying updateActor(RequestBody requestBody, @PathParam("actorId") long id) {
        Optional<Actor> actorOptional = dao.findById(id);
        if(!actorOptional.isPresent()) {
            throw new NotFoundException("No such  actor.");
        }
        Actor actor = actorOptional.get();
        if(requestBody.actorName != null) actor.setName(requestBody.actorName);
        if(requestBody.actorBirthDate != null) actor.setBirthDate(requestBody.actorBirthDate);

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
