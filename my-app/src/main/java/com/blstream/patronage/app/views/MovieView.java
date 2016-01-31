package com.blstream.patronage.app.views;

import com.blstream.patronage.app.model.Movie;
import io.dropwizard.views.View;

import java.util.List;

@SuppressWarnings("unused")
public class MovieView extends View {
    private final List<Movie> movie;

    public MovieView(List<Movie> movies) {
        super("movie.mustache");
        this.movie = movies;
    }

    public List<Movie> getMovie() {
        return movie;
    }
}
