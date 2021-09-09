package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import java.util.Optional;

import it.unifi.dinfo.swam.plantnursery.nosql.model.Example;
import jakarta.inject.Inject;
import jakarta.nosql.mapping.column.ColumnTemplate;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("example")
public class ExampleEndpoint {
	
	@Inject
    private ColumnTemplate columnTemplate;
	
	@GET
	@Path("{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExamples(@PathParam("name") String name) {
		Optional<Example> species = columnTemplate.find(Example.class, name);
		Example ex = species.get();
		return Response.ok().entity(ex).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveExample(Example ex) {
		ex = columnTemplate.insert(ex);
		return Response.ok().entity(ex).build();
	}
}
