import conf.MyConfiguration;
import health.TemplateHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import resources.MovieResource;

/**
 * Created by VanDavv on 2015-12-12.
 */
public class MyApplication extends Application<MyConfiguration> {

    public static void main(String[] args) throws Exception {
        new MyApplication().run("server", "configuration.yml");
    }

    @Override
    public String getName(){
        return "Hello World!";
    }

    @Override
    public void initialize(Bootstrap<MyConfiguration> bootstrap){

    }

    @Override
    public void run(MyConfiguration myConfiguration, Environment environment) throws Exception {
        final MovieResource resource = new MovieResource(
                myConfiguration.getTemplate(),
                myConfiguration.getDefaultName(),
                dao);

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(myConfiguration.getTemplate());

        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }
}
