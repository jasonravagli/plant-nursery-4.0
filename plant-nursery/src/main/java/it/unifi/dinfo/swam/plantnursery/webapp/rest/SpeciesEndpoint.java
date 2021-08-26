package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.unifi.dinfo.swam.plantnursery.controller.SpeciesController;
import it.unifi.dinfo.swam.plantnursery.model.PlantType;
import it.unifi.dinfo.swam.plantnursery.model.Species;

@Path("species")
public class SpeciesEndpoint {
	
	@Inject
	private SpeciesController speciesController;
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSpecies(@PathParam("id") Long id) {
		speciesController.deleteSpecies(id);
		
		if(speciesController.isErrorOccurred()) {
			throw new BadRequestException(speciesController.getErrorMessage());
		}
		
		return Response.ok().build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecies(@QueryParam("plantType") PlantType plantType, @QueryParam("name") String name) {
		List<Species> species = speciesController.getFilteredSpecies(plantType, name);
		
		if(speciesController.isErrorOccurred()) {
			throw new BadRequestException(speciesController.getErrorMessage());
		}
		
		return Response.ok().entity(species).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpeciesById(@PathParam("id") Long id) {
		Species species = speciesController.getSpeciesById(id);
		
		if(speciesController.isErrorOccurred()) {
			throw new BadRequestException(speciesController.getErrorMessage());
		}
		
		return Response.ok().entity(species).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveSpecies(Species species) {
		speciesController.saveSpecies(species);
		
		if(speciesController.isErrorOccurred()) {
			throw new BadRequestException(speciesController.getErrorMessage());
		}
		
		return Response.ok().build();
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSpecies(@PathParam("id") Long idSpecies, Species species) {
		if(idSpecies != species.getId()) {
			throw new BadRequestException("Invalid URI: inconsistency with the id of the species to update");
		}
		
		speciesController.updateSpecies(species);
		
		if(speciesController.isErrorOccurred()) {
			throw new BadRequestException(speciesController.getErrorMessage());
		}
		
		return Response.ok().build();
	}
	
}