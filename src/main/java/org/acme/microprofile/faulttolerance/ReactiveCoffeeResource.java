package org.acme.microprofile.faulttolerance;

import io.smallrye.mutiny.Uni;
import org.acme.microprofile.faulttolerance.service.ReactiveCoffeeService;
import org.acme.microprofile.faulttolerance.vo.Coffee;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;


@Path("reactive_coffee")
@Produces(MediaType.APPLICATION_JSON)
public class ReactiveCoffeeResource {

    @Inject ReactiveCoffeeService coffeeService;

    private static final Logger LOGGER = Logger.getLogger(CoffeeResource.class);
    private final AtomicLong counter = new AtomicLong(0);

    @GET
    public Uni<List<Coffee>> list(){
        final Long invocationNumber = counter.getAndIncrement();
        maybeFail(String.format("CoffeeResource#coffees() invocation #%d failed", invocationNumber));
        LOGGER.infof("CoffeeResource#coffees() invocation #%d returning successfully", invocationNumber);
        return coffeeService.list();
    }

    private void maybeFail(String failureLogMessage) {
        if (new Random().nextBoolean()) {
            LOGGER.error(failureLogMessage);
            throw new RuntimeException("Resource failure.");
        }
    }
}
