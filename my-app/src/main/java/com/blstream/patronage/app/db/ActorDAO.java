package com.blstream.patronage.app.db;

import com.blstream.patronage.app.exceptions.DataAccessException;
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

    public Actor findById(Long id)throws DataAccessException {
        if(get(id) == null) {
            throw new DataAccessException("Movie not found!");
        }
        return get(id);
    }

    public Actor create(Actor actor) {
        return persist(actor);
    }

    public List<Actor> findAll() {
        return list(currentSession().createCriteria(Actor.class));
    }

    public void delete(Long id) throws DataAccessException {
        Actor actor = findById(id);
        currentSession().delete(actor);
    }
    public Actor update(Actor actor) {
        return persist(actor);
    }
}
