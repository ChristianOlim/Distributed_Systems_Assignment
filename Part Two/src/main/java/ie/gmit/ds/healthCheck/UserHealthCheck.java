package ie.gmit.ds.healthCheck;

import com.codahale.metrics.health.HealthCheck;

public class UserHealthCheck extends HealthCheck {
    // Resource: https://github.com/john-french/artistAPI-dropwizard-soln
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
