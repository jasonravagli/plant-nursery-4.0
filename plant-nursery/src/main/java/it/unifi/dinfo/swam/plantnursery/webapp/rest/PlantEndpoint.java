package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.unifi.dinfo.swam.plantnursery.controller.PlantController;
import it.unifi.dinfo.swam.plantnursery.dto.PlantDto;

@Path("plants")
public class PlantEndpoint {

	@Inject
	private PlantController plantController;

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePlant(@PathParam("id") Long id) {
		plantController.deletePlant(id);

		if (plantController.isErrorOccurred()) {
			throw new BadRequestException(plantController.getErrorMessage());
		}

		return Response.ok().build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlants(@QueryParam("id-gp") Long idGrowthPlace, @QueryParam("id-species") Long idSpecies,
			@QueryParam("sold") Boolean sold, @QueryParam("date-start") LocalDate dateStart,
			@QueryParam("date-end") LocalDate dateEnd) {
		List<PlantDto> plants = plantController.getFilteredPlants(idGrowthPlace, idSpecies, sold, dateStart, dateEnd);

		if (plantController.isErrorOccurred()) {
			throw new BadRequestException(plantController.getErrorMessage());
		}

		return Response.ok().entity(plants).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlantById(@PathParam("id") Long id) {
		PlantDto plant = plantController.getPlantById(id);

		if (plantController.isErrorOccurred()) {
			throw new BadRequestException(plantController.getErrorMessage());
		}

		return Response.ok().entity(plant).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response savePlant(PlantDto plant) {
		plantController.savePlant(plant);

		if (plantController.isErrorOccurred()) {
			throw new BadRequestException(plantController.getErrorMessage());
		}

		return Response.ok().build();
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePlant(@PathParam("id") Long id, PlantDto plant) {
		if(id != plant.getId()) {
			throw new BadRequestException("Invalid URI: inconsistency with the id of the species to update");
		}
		
		plantController.updatePlant(plant);

		if (plantController.isErrorOccurred()) {
			throw new BadRequestException(plantController.getErrorMessage());
		}

		return Response.ok().build();
	}
}
