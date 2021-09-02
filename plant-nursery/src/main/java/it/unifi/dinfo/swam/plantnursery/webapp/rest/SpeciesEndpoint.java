package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import it.unifi.dinfo.swam.plantnursery.controller.SpeciesController;
import it.unifi.dinfo.swam.plantnursery.dto.SpeciesDto;
import it.unifi.dinfo.swam.plantnursery.model.PlantType;
import it.unifi.dinfo.swam.plantnursery.model.Species;

@Path("species")
public class SpeciesEndpoint {

	@Inject
	private SpeciesController speciesController;

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSpecies(@PathParam("id") Long id) {
		System.out.println("service delete: id " + id);
		speciesController.deleteSpecies(id);
		
		if (speciesController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(speciesController.getErrorMessage()).build();
		}

		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecies(@QueryParam("plantType") PlantType plantType, @QueryParam("name") String name) {
		List<SpeciesDto> species = speciesController.getFilteredSpecies(plantType, name);

		if (speciesController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(speciesController.getErrorMessage()).build();
		}

		return Response.ok().entity(species).build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpeciesById(@PathParam("id") Long id) {
		Species species = speciesController.getSpeciesById(id);

		if (speciesController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(speciesController.getErrorMessage()).build();
		}

		return Response.ok().entity(species).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveSpecies(Species species) {
		speciesController.saveSpecies(species);

		if (speciesController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(speciesController.getErrorMessage()).build();
		}

		return Response.ok().build();
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSpecies(@PathParam("id") Long idSpecies, Species species) {
		speciesController.updateSpecies(idSpecies, species);

		if (speciesController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(speciesController.getErrorMessage()).build();
		}

		return Response.ok().build();
	}

}
