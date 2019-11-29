package ie.gmit.ds;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserApiApplication extends Application<UserApiConfig> {
    // Resource: https://github.com/john-french/artistAPI-dropwizard-soln
    // Resource: https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/?fbclid=IwAR1Vq7ThFJHBb_9BmhWANUcokeXdJ6_gvRni4SXHqLuWQLZFTBV844KgVEo
    private static final Logger LOGGER = LoggerFactory.getLogger(UserApiApplication.class);

    public static void main(String[] args) throws Exception {
        new UserApiApplication().run(args);
    }

    @Override
    public void run(UserApiConfig userApiConfig, Environment environment) throws Exception {
        LOGGER.info("Registering REST resources");

        environment.healthChecks().register("UserHealthCheck", new UserHealthCheck());
        environment.jersey().register(new UserApiResource(environment.getValidator()));
    }

}