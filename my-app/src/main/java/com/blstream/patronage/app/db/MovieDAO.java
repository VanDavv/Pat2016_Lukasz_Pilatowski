package com.blstream.patronage.app.db;

import com.blstream.patronage.app.exceptions.DataAccessException;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import com.blstream.patronage.app.model.Movie;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by VanDavv on 2015-12-13.
 */
public class MovieDAO extends AbstractDAO<Movie> {
    public MovieDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    public Movie findById(Long id) throws DataAccessException {
        if(get(id) == null) {
            throw new DataAccessException("Movie not found!");
        }
        return get(id);
    }

    public Movie create(Movie movie) {
        return persist(movie);
    }

    public List<Movie> findAll() {
        return list(currentSession().createCriteria(Movie.class));
    }

    public void delete(Long id) throws DataAccessException {
        Movie movie = findById(id);
        currentSession().delete(movie);
    }
    public Movie update(Movie movie) {
        return persist(movie);
    }
}
