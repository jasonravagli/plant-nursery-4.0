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
//import it.unifi.dinfo.swam.plantnursery.controller.GrowthPlaceController;
//import it.unifi.dinfo.swam.plantnursery.dto.GrowthPlaceDto;
//
//@Path("growth-places")
//public class GrowthPlaceEndpoint {
//	
//	@Inject
//	private GrowthPlaceController growthPlaceController;
//	
//	@DELETE
//	@Path("{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response deleteGrowthPlace(@PathParam("id") Long id) {
//		growthPlaceController.deleteGrowthPlace(id);
//		
//		if(growthPlaceController.isErrorOccurred()) {
//			throw new BadRequestException(growthPlaceController.getErrorMessage());
//		}
//		
//		return Response.ok().build();
//	}
//	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getGrowthPlaces(@QueryParam("name") String prefixName) {
//		List<GrowthPlaceDto> growthPlaces = growthPlaceController.getGrowthPlaces(prefixName);
//		
//		return Response.ok().entity(growthPlaces).build();
//	}
//	
//	@GET
//	@Path("{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getGrowthPlaceById(@PathParam("id") Long id) {
//		GrowthPlaceDto growthPlace = growthPlaceController.getGrowthPlaceById(id);
//		
//		return Response.ok().entity(growthPlace).build();
//	}
//	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response saveGrowthPlace(GrowthPlaceDto growthPlace) {
//		growthPlaceController.saveGrowthPlace(growthPlace);
//		
//		if(growthPlaceController.isErrorOccurred()) {
//			throw new BadRequestException(growthPlaceController.getErrorMessage());
//		}
//		
//		return Response.ok().build();
//	}
//	
//	@PUT
//	@Path("{id}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateGrowthPlace(@PathParam("id") Long id, GrowthPlaceDto growthPlace) {
//		if(id != growthPlace.getId()) {
//			throw new BadRequestException("Invalid URI: inconsistency with the id of the growth place to update");
//		}
//		
//		growthPlaceController.updateGrowthPlace(growthPlace);
//		
//		if(growthPlaceController.isErrorOccurred()) {
//			throw new BadRequestException(growthPlaceController.getErrorMessage());
//		}
//		
//		return Response.ok().build();
//	}
//}
