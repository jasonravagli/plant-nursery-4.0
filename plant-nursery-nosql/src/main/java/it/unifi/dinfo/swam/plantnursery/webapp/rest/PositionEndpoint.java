package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import java.util.List;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import it.unifi.dinfo.swam.plantnursery.nosql.controller.PositionController;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.PositionDto;

@Path("growth-places/{id-gp}/positions")
public class PositionEndpoint {

	@Inject
	private PositionController positionController;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPositionsByGrowthPlace(@PathParam("id-gp") UUID idGrowthPlace) {
		List<PositionDto> positions = positionController.getPositionsByGrowthPlace(idGrowthPlace);

		if (positionController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(positionController.getErrorMessage()).build();
		}

		return Response.ok().entity(positions).build();
	}

	@GET
	@Path("free")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlantFreePositionsByGrowthPlace(@PathParam("id-gp") UUID idGrowthPlace) {
		List<PositionDto> freePositions = positionController.getPlantFreePositionsByGrowthPlace(idGrowthPlace);

		if (positionController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(positionController.getErrorMessage()).build();
		}

		return Response.ok().entity(freePositions).build();
	}

	@PUT
	@Path("{id-pos}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePosition(@PathParam("id-gp") UUID idGrowthPlace, @PathParam("id-pos") UUID idPosition,
			PositionDto positionDto) {
		positionController.updatePosition(idGrowthPlace, idPosition, positionDto);

		if (positionController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(positionController.getErrorMessage()).build();
		}

		return Response.ok().build();
	}
}
