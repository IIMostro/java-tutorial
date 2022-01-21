package org.ilmostro.quarkus.controller;

import io.vertx.core.Vertx;
import org.ilmostro.quarkus.service.SimpleService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/simple")
public class SimpleController {

    @Inject
    SimpleService service;

    @Inject
    Vertx vertx;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/say")
    public String say() {
        return service.say();
    }
}
