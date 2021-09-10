package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationPath("rest")
public class PlantNurseryApplication extends Application {
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello(){
        return Response.ok("Hello from the plant nursery 4.0  NoSql server").build();
    }
}
