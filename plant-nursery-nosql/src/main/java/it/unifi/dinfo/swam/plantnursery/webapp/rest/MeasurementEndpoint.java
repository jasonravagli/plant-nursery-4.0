package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import it.unifi.dinfo.swam.plantnursery.nosql.controller.MeasurementController;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.MeasurementDto;
import it.unifi.dinfo.swam.plantnursery.utils.LocalDateTimeJacksonDeserializer;
import it.unifi.dinfo.swam.plantnursery.utils.Utilities;
import tech.tablesaw.api.Table;

@Path("measurements")
public class MeasurementEndpoint {

	@Inject
	private MeasurementController measurementController;

	@GET
	@Path("growth-place/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getMeasurementsByGrowthPlace(@PathParam("id") UUID idGrowthPlace,
			@QueryParam("start-date") @JsonDeserialize(using = LocalDateTimeJacksonDeserializer.class) LocalDateTime startDateTime,
			@QueryParam("end-date") @JsonDeserialize(using = LocalDateTimeJacksonDeserializer.class) LocalDateTime endDateTime) {
		Table tableMeasures = measurementController.getMeasurementsByGrowthPlace(idGrowthPlace, startDateTime,
				endDateTime);

		if (measurementController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(measurementController.getErrorMessage()).build();
		}

		String outputString = null;
		try {
			outputString = Utilities.convertTableToCsvString(tableMeasures);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error writing csv result").build();
		}

		return Response.ok().entity(outputString).build();
	}

	@GET
	@Path("plant/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getMeasurementsByPlant(@PathParam("id") UUID idPlant,
			@QueryParam("start-date") @JsonDeserialize(using = LocalDateTimeJacksonDeserializer.class) LocalDateTime startDateTime,
			@QueryParam("end-date") @JsonDeserialize(using = LocalDateTimeJacksonDeserializer.class) LocalDateTime endDateTime) {
		Table tableMeasures = measurementController.getMeasurementsByPlant(idPlant, startDateTime, endDateTime);

		if (measurementController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(measurementController.getErrorMessage()).build();
		}

		String outputString = null;
		try {
			outputString = Utilities.convertTableToCsvString(tableMeasures);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error writing csv result").build();
		}

		return Response.ok().entity(outputString).build();
	}

	@GET
	@Path("sensor/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getMeasurementsBySensor(@PathParam("id") UUID idSensor,
			@QueryParam("start-date") @JsonDeserialize(using = LocalDateTimeJacksonDeserializer.class) LocalDateTime startDateTime,
			@QueryParam("end-date") @JsonDeserialize(using = LocalDateTimeJacksonDeserializer.class) LocalDateTime endDateTime) {
		Table tableMeasures = measurementController.getMeasurementsBySensor(idSensor, startDateTime, endDateTime);

		if (measurementController.isErrorOccurred()) {
			return Response.status(Status.BAD_REQUEST).entity(measurementController.getErrorMessage()).build();
		}

		String outputString = null;
		try {
			outputString = Utilities.convertTableToCsvString(tableMeasures);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error writing csv result").build();
		}

		return Response.ok().entity(outputString).build();
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
