//package it.unifi.dinfo.swam.plantnursery.webapp.rest;
//
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.ws.rs.BadRequestException;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import it.unifi.dinfo.swam.plantnursery.controller.SensorController;
//import it.unifi.dinfo.swam.plantnursery.model.Sensor;
//
//@Path("sensors")
//public class SensorEndpoint {
//	
//	@Inject
//	private SensorController sensorController;
//	
//	@DELETE
//	@Path("{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response deleteSensor(@PathParam("id") Long id) {
//		sensorController.deleteSensor(id);
//
//		if (sensorController.isErrorOccurred()) {
//			throw new BadRequestException(sensorController.getErrorMessage());
//		}
//
//		return Response.ok().build();
//	}
//	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getSensors(@QueryParam("id-gp") Long idGrowthPlace, @QueryParam("company") String company,
//			@QueryParam("model") String model, @QueryParam("mac-address") String macAddress,
//			@QueryParam("active") Boolean active) {
//		List<Sensor> sensors = sensorController.getFilteredSensors(idGrowthPlace, company, model, macAddress, active);
//
//		if (sensorController.isErrorOccurred()) {
//			throw new BadRequestException(sensorController.getErrorMessage());
//		}
//
//		return Response.ok().entity(sensors).build();
//	}
//	
//	@GET
//	@Path("{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getSensorById(@PathParam("id") Long id) {
//		Sensor sensor = sensorController.getSensorById(id);
//
//		if (sensorController.isErrorOccurred()) {
//			throw new BadRequestException(sensorController.getErrorMessage());
//		}
//
//		return Response.ok().entity(sensor).build();
//	}
//	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response saveSensor(Sensor sensor) {
//		sensorController.saveSensor(sensor);
//
//		if (sensorController.isErrorOccurred()) {
//			throw new BadRequestException(sensorController.getErrorMessage());
//		}
//
//		return Response.ok().build();
//	}
//	
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateSensor(Sensor sensor) {
//		sensorController.updateSensor(sensor);
//
//		if (sensorController.isErrorOccurred()) {
//			throw new BadRequestException(sensorController.getErrorMessage());
//		}
//
//		return Response.ok().build();
//	}
//}
