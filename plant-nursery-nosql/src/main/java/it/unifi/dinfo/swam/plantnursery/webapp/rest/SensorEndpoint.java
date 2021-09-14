package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import java.util.List;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.controller.SensorController;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.SensorDto;
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

@Path("sensors")
public class SensorEndpoint {
	
	@Inject
	private SensorController sensorController;
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSensor(@PathParam("id") UUID id) {
		sensorController.deleteSensor(id);

		if (sensorController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(sensorController.getErrorMessage()).build();
		}

		return Response.ok().build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSensors(@QueryParam("id-gp") UUID idGrowthPlace, @QueryParam("company") String company,
			@QueryParam("model") String model, @QueryParam("mac-address") String macAddress) {
		List<SensorDto> sensors = sensorController.getFilteredSensors(idGrowthPlace, company, model, macAddress);

		if (sensorController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(sensorController.getErrorMessage()).build();
		}

		return Response.ok().entity(sensors).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSensorById(@PathParam("id") UUID id) {
		SensorDto sensor = sensorController.getSensorById(id);

		if (sensorController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(sensorController.getErrorMessage()).build();
		}
		
		return Response.ok().entity(sensor).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveSensor(SensorDto sensor) {
		sensorController.saveSensor(sensor);

		if (sensorController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(sensorController.getErrorMessage()).build();
		}

		return Response.ok().build();
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSensor(@PathParam("id") UUID idSensor, SensorDto sensor) {
		sensorController.updateSensor(idSensor, sensor);

		if (sensorController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(sensorController.getErrorMessage()).build();
		}

		return Response.ok().build();
	}
}
