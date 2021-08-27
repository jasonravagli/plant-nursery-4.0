//package it.unifi.dinfo.swam.plantnursery.webapp.rest;
//
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.ws.rs.BadRequestException;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import it.unifi.dinfo.swam.plantnursery.controller.PositionController;
//import it.unifi.dinfo.swam.plantnursery.dto.PositionDto;
//
//@Path("growth-places/{id-gp}/positions")
//public class PositionEndpoint {
//	
//	@Inject
//	private PositionController positionController;
//	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getPositionsByGrowthPlace(@PathParam("id-gp") Long idGrowthPlace) {
//		List<PositionDto> positions = positionController.getPlantFreePositionsByGrowthPlace(idGrowthPlace);
//		
//		if(positionController.isErrorOccurred()) {
//			throw new BadRequestException(positionController.getErrorMessage());
//		}
//		
//		return Response.ok().entity(positions).build();
//	}
//	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getPlantFreePositionsByGrowthPlace(@PathParam("id-gp") Long idGrowthPlace) {
//		List<PositionDto> freePositions = positionController.getPlantFreePositionsByGrowthPlace(idGrowthPlace);
//		
//		if(positionController.isErrorOccurred()) {
//			throw new BadRequestException(positionController.getErrorMessage());
//		}
//		
//		return Response.ok().entity(freePositions).build();
//	}
//	
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updatePosition(@PathParam("id-gp") Long idGrowthPlace, PositionDto positionDto) {
//		if(idGrowthPlace != positionDto.getGrowthPlaceId()) {
//			throw new BadRequestException("Invalid URI: inconsistency with the growth place id of the position");
//		}
//		
//		positionController.updatePosition(positionDto);
//		
//		if(positionController.isErrorOccurred()) {
//			throw new BadRequestException(positionController.getErrorMessage());
//		}
//		
//		return Response.ok().build();
//	}
//}
