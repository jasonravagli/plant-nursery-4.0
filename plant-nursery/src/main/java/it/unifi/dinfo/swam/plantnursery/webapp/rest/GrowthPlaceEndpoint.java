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

import it.unifi.dinfo.swam.plantnursery.controller.GrowthPlaceController;
import it.unifi.dinfo.swam.plantnursery.dto.GrowthPlaceDto;

@Path("growth-places")
public class GrowthPlaceEndpoint {

	@Inject
	private GrowthPlaceController growthPlaceController;

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteGrowthPlace(@PathParam("id") Long id) {
		growthPlaceController.deleteGrowthPlace(id);

		if (growthPlaceController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(growthPlaceController.getErrorMessage()).build();
		}

		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGrowthPlaces(@QueryParam("name") String prefixName) {
		List<GrowthPlaceDto> growthPlaces = growthPlaceController.getGrowthPlaces(prefixName);

		if (growthPlaceController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(growthPlaceController.getErrorMessage()).build();
		}

		return Response.ok().entity(growthPlaces).build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGrowthPlaceById(@PathParam("id") Long id) {
		GrowthPlaceDto growthPlace = growthPlaceController.getGrowthPlaceById(id);

		if (growthPlaceController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(growthPlaceController.getErrorMessage()).build();
		}

		return Response.ok().entity(growthPlace).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveGrowthPlace(GrowthPlaceDto growthPlace) {
		growthPlaceController.saveGrowthPlace(growthPlace);

		if (growthPlaceController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(growthPlaceController.getErrorMessage()).build();
		}

		return Response.ok().build();
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateGrowthPlace(@PathParam("id") Long idGrowthPlace, GrowthPlaceDto growthPlace) {
		growthPlaceController.updateGrowthPlace(idGrowthPlace, growthPlace);

		if (growthPlaceController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(growthPlaceController.getErrorMessage()).build();
		}

		return Response.ok().build();
	}
}
