package bundle;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by VanDavv on 2016-01-21.
 */
public abstract class MovieBundle<E extends Configuration> implements ConfiguredBundle<E> {
    Client client;
    UriBuilder uriBuilder;
    @Override
    public void run(E myConfiguration, Environment environment) throws Exception {
        client = JerseyClientBuilder.newBuilder().build();
        uriBuilder.uri(getResourceURI(myConfiguration));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }
    abstract public URI getResourceURI(E configuration);

    public MovieProvider getMovieProvider() {
        return new MovieProviderImpl(client, uriBuilder);
    }
}
