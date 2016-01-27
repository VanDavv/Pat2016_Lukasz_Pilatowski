package com.blstream.patronage.app.views;

import com.blstream.patronage.app.model.Movie;
import io.dropwizard.views.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class MovieView extends View {
    private final Set<Map.Entry<Movie, com.blstream.patronage.movieDataBundle.Movie>> movie;

    public MovieView(List<Movie> movies) {
        super("movie.mustache");

        Map<Movie, com.blstream.patronage.movieDataBundle.Movie> mMap = new HashMap<>();
        for (Movie m : movies) {
            mMap.put(m, m.getDetailedMovieData());
        }
        movie = mMap.entrySet();
    }

    public Set<Map.Entry<Movie, com.blstream.patronage.movieDataBundle.Movie>> getMovie() {
        return movie;
    }
}
