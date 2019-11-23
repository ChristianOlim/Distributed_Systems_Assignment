package ie.gmit.ds;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class UserApiApplication  extends Application<UserApiConfig> {
    // Resource: https://github.com/john-french/artistAPI-dropwizard-soln

    public static void main(String[] args) throws Exception {
        new UserApiApplication().run(args);
    }

    public void run(UserApiConfig userApiConfig, Environment environment) throws Exception {

        final UserApiResource resource =
                new UserApiResource();

        // Registered a health check
        final UserHealthCheck healthCheck =
                new UserHealthCheck();

        environment.healthChecks().register("example", healthCheck);
        environment.jersey().register(resource);
    }

}