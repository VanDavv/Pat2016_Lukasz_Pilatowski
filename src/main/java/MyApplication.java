import conf.MyConfiguration;
import db.ActorDAO;
import db.MovieDAO;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import models.Actor;
import models.Movie;
import resources.ActorResource;
import resources.MovieResource;

import java.net.URI;
import java.net.URISyntaxException;

public class MyApplication extends Application<MyConfiguration> {

    private final HibernateBundle<MyConfiguration> hibernateBundle =
            new HibernateBundle<MyConfiguration>(Movie.class, Actor.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(MyConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    private final MovieBundle<MyConfiguration> movieProvider =
            new MovieBundle<MyConfiguration>() {
                @Override
                public URI getResourceURI(MyConfiguration configuration) {
                    try {
                        return new URI(configuration.getMovieProviderURL());
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        throw new RuntimeException("URISyntaxException");
                    }
                }
            };

    public static void main(String[] args) throws Exception {
        new MyApplication().run("server", "configuration.yml");
    }

    @Override
    public String getName(){
        return "MyApp";
    }

    @Override
    public void initialize(Bootstrap<MyConfiguration> bootstrap){
        bootstrap.addBundle(hibernateBundle);
    }



    @Override
    public void run(MyConfiguration myConfiguration, Environment environment) throws Exception {
        final MovieDAO movieDAO = new MovieDAO(hibernateBundle.getSessionFactory());
        final ActorDAO actorDAO = new ActorDAO(hibernateBundle.getSessionFactory());

        final MovieResource movieResource = new MovieResource(movieDAO);
        final ActorResource actorResource = new ActorResource(actorDAO);

        environment.jersey().register(movieResource);
        environment.jersey().register(actorResource);
    }
}
