package com.blstream.patronage.app.db;

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
    public Optional<Movie> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public Movie create(Movie movie) {
        return persist(movie);
    }

    public List<Movie> findAll() {
        return list(currentSession().createCriteria(Movie.class));
    }

    public Optional<Movie> delete(Long id) {
        Optional<Movie> movie = Optional.fromNullable(get(id));
        if(movie.isPresent()) {
            currentSession().delete(movie.get());
        }
        return movie;
    }
    public Movie update(Movie movie) {
        return persist(movie);
    }
}
