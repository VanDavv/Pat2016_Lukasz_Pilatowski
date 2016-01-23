package com.blstream.patronage.movieDataBundle;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * Created by VanDavv on 2016-01-21.
 */
class MovieProviderImpl implements MovieProvider {
    private final Client client;
    private final UriBuilder uriBuilder;

    public MovieProviderImpl(Client client, UriBuilder uriBuilder) {
        this.client = client;
        this.uriBuilder = uriBuilder;
    }

    @Override
    public Movie getMovieData(String movieTitle) {
        uriBuilder.queryParam("t", movieTitle);
        MovieRepresentation representation = client
                .target(uriBuilder)
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .buildGet()
                .invoke()
                .readEntity(MovieRepresentation.class);
        return representation.convertToMovie();
    }
}
