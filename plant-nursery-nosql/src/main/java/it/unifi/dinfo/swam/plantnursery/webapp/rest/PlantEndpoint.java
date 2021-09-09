package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import java.time.LocalDate;
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
import it.unifi.dinfo.swam.plantnursery.dto.PlantDto;
import it.unifi.dinfo.swam.plantnursery.sql.controller.SqlPlantController;

@Path("plants")
public class PlantEndpoint {

	@Inject
	private SqlPlantController plantController;

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePlant(@PathParam("id") Long id) {
		plantController.deletePlant(id);

		if (plantController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(plantController.getErrorMessage()).build();
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
			return Response.status(Status.BAD_REQUEST).entity(plantController.getErrorMessage()).build();
		}

		return Response.ok().entity(plants).build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlantById(@PathParam("id") Long id) {
		PlantDto plant = plantController.getPlantById(id);

		if (plantController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(plantController.getErrorMessage()).build();
		}

		return Response.ok().entity(plant).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response savePlant(PlantDto plant) {
		plantController.savePlant(plant);

		if (plantController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(plantController.getErrorMessage()).build();
		}

		return Response.ok().build();
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePlant(@PathParam("id") Long id, PlantDto plant) {
		plantController.updatePlant(id, plant);

		if (plantController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(plantController.getErrorMessage()).build();
		}

		return Response.ok().build();
	}
}
