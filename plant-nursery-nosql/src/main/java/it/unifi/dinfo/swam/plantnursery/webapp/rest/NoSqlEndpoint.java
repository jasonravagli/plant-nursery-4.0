package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.NoSqlSpeciesDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.NoSqlSpeciesById;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("nosql")
public class NoSqlEndpoint {
	
	@Inject
	private NoSqlSpeciesDao speciesDao;
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpeciesById(@PathParam("id") String id) {
		System.out.println("EHI");
		NoSqlSpeciesById species = speciesDao.findById(UUID.fromString(id));
		System.out.println("species: " + species);

		return Response.ok().entity(species).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveSpecies(NoSqlSpeciesById species) {
		species = speciesDao.save(species);

		return Response.ok().entity(species).build();
	}
	
}
