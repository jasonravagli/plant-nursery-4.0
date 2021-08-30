package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.unifi.dinfo.swam.plantnursery.controller.MeasurementController;
import it.unifi.dinfo.swam.plantnursery.dto.MeasurementDto;
import tech.tablesaw.api.Table;

@Path("measurements")
public class MeasurementEndpoint {

	@Inject
	private MeasurementController measurementController;

	@GET
	@Path("growth-place/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMeasurementsByGrowthPlace(@PathParam("id") Long idGrowthPlace,
			@QueryParam("start-date") String startDateTimeStr, @QueryParam("end-date") String endDateTimeStr) {
		
		System.out.println(startDateTimeStr);
		System.out.println(endDateTimeStr);
		LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr);
		LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr);
		Table tableMeasures = measurementController.getMeasurementsByGrowthPlace(idGrowthPlace, startDateTime,
				endDateTime);

		if (measurementController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(measurementController.getErrorMessage()).build();
		}

		return Response.ok().entity(tableMeasures).build();
	}

	@GET
	@Path("plant/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMeasurementsByPlant(@PathParam("id") Long idPlant,
			@QueryParam("start-date") LocalDateTime startDateTime, @QueryParam("end-date") LocalDateTime endDateTime) {
		Table tableMeasures = measurementController.getMeasurementsByPlant(idPlant, startDateTime, endDateTime);

		if (measurementController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(measurementController.getErrorMessage()).build();
		}

		return Response.ok().entity(tableMeasures).build();
	}
	
	@GET
	@Path("sensor/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMeasurementsBySensor(@PathParam("id") Long idSensor,
			@QueryParam("start-date") LocalDateTime startDateTime, @QueryParam("end-date") LocalDateTime endDateTime) {
		Table tableMeasures = measurementController.getMeasurementsBySensor(idSensor, startDateTime, endDateTime);

		if (measurementController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(measurementController.getErrorMessage()).build();
		}

		return Response.ok().entity(tableMeasures).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveMeasurement(MeasurementDto measurementDto) {
		measurementController.saveMeasurement(measurementDto);

		if (measurementController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(measurementController.getErrorMessage()).build();
		}

		return Response.ok().build();
	}
}
