package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.unifi.dinfo.swam.plantnursery.dao.SpeciesDao;
import it.unifi.dinfo.swam.plantnursery.model.Species;

@Path("catalog")
public class CatalogEndpoint {
	
	@Inject
	private SpeciesDao speciesDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecies() {
		List<Species> species = speciesDao.getSpecies();
		
		return Response.ok().entity(species).build();
	}
}
