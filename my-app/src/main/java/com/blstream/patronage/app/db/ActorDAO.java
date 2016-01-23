package com.blstream.patronage.app.db;

import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import com.blstream.patronage.app.model.Actor;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by VanDavv on 2015-12-13.
 */
public class ActorDAO extends AbstractDAO<Actor> {
    public ActorDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    public Optional<Actor> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public Actor create(Actor actor) {
        return persist(actor);
    }

    public List<Actor> findAll() {
        return list(currentSession().createCriteria(Actor.class));
    }

    public Optional<Actor> delete(Long id) {
        Optional<Actor> actor = Optional.fromNullable(get(id));
        if(actor.isPresent()) {
            currentSession().delete(actor.get());
        }
        return actor;
    }
    public Actor update(Actor actor) {
        return persist(actor);
    }
}
