//package it.unifi.dinfo.swam.plantnursery.webapp.rest;
//
//import java.time.LocalDateTime;
//import javax.inject.Inject;
//import javax.ws.rs.BadRequestException;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import it.unifi.dinfo.swam.plantnursery.controller.MeasurementController;
//import it.unifi.dinfo.swam.plantnursery.model.Measurement;
//import tech.tablesaw.api.Table;
//
//@Path("measurements")
//public class MeasurementEndpoint {
//
//	@Inject
//	private MeasurementController measurementController;
//
//	@GET
//	@Path("growth-place/{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getMeasurementsByGrowthPlace(@PathParam("id") Long idGrowthPlace,
//			@QueryParam("start-date") LocalDateTime startDateTime, @QueryParam("end-date") LocalDateTime endDateTime) {
//		Table tableMeasures = measurementController.getMeasurementsByGrowthPlace(idGrowthPlace, startDateTime,
//				endDateTime);
//
//		if (measurementController.isErrorOccurred()) {
//			throw new BadRequestException(measurementController.getErrorMessage());
//		}
//
//		return Response.ok().entity(tableMeasures).build();
//	}
//
//	@GET
//	@Path("plant/{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getMeasurementsByPlant(@PathParam("id") Long idPlant,
//			@QueryParam("start-date") LocalDateTime startDateTime, @QueryParam("end-date") LocalDateTime endDateTime) {
//		Table tableMeasures = measurementController.getMeasurementsByPlant(idPlant, startDateTime, endDateTime);
//
//		if (measurementController.isErrorOccurred()) {
//			throw new BadRequestException(measurementController.getErrorMessage());
//		}
//
//		return Response.ok().entity(tableMeasures).build();
//	}
//	
//	@GET
//	@Path("sensor/{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getMeasurementsBySensor(@PathParam("id") Long idSensor,
//			@QueryParam("start-date") LocalDateTime startDateTime, @QueryParam("end-date") LocalDateTime endDateTime) {
//		Table tableMeasures = measurementController.getMeasurementsBySensor(idSensor, startDateTime, endDateTime);
//
//		if (measurementController.isErrorOccurred()) {
//			throw new BadRequestException(measurementController.getErrorMessage());
//		}
//
//		return Response.ok().entity(tableMeasures).build();
//	}
//	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response saveMeasurement(Measurement measurement) {
//		measurementController.saveMeasurement(measurement);
//
//		if (measurementController.isErrorOccurred()) {
//			throw new BadRequestException(measurementController.getErrorMessage());
//		}
//
//		return Response.ok().build();
//	}
//}
