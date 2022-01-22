package org.ilmostro.quarkus.controller;

import org.ilmostro.quarkus.service.RedisClientService;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * @author li.bowei
 */

@Path("/redis")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RedisController {

    @Inject
    RedisClientService service;

    @GET
    @Path("/get")
    public String get(String key){
        return service.read(key);
    }

    @GET
    @Path("/set")
    public String set(String key, String value){
        service.set(key, value);
        return null;
    }
}
