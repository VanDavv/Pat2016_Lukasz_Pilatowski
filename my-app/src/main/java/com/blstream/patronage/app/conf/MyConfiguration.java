package com.blstream.patronage.app.conf;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by VanDavv on 2015-12-12.
 */
public class MyConfiguration extends Configuration {

    @JsonProperty
    @NotNull
    private DataSourceFactory database;

    @JsonProperty
    @NotEmpty
    private String movieProviderURL;


    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public String getMovieProviderURL() {
        return movieProviderURL;
    }
}
