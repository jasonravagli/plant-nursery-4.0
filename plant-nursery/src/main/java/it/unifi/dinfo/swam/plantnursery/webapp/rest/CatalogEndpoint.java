package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.unifi.dinfo.swam.plantnursery.controller.SpeciesController;
import it.unifi.dinfo.swam.plantnursery.model.PlantType;
import it.unifi.dinfo.swam.plantnursery.model.Species;

@Path("catalog")
public class CatalogEndpoint {
	
	@Inject
	private SpeciesController speciesController;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecies(@QueryParam("plantType") PlantType plantType, @QueryParam("name") String name) {
		List<Species> species = speciesController.getFilteredSpecies(plantType, name);
		
		return Response.ok().entity(species).build();
	}
}
