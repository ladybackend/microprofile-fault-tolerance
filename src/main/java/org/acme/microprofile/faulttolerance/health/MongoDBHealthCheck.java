package org.acme.microprofile.faulttolerance.health;


import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;


@Readiness
@ApplicationScoped
public class MongoDBHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Database connection health check");

        try {
            responseBuilder.up().
                    withData("CoffeeCollection","OK");

        } catch (IllegalStateException e) {
            responseBuilder.down()
                    .withData("Error" , e.getMessage());
        }

        return responseBuilder.build();
    }
}