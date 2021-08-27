package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationPath("rest")
public class PlantNurseryApplication extends Application {
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello(){
        return Response.ok("Hello from the plant nursery 4.0 server").build();
    }
}
