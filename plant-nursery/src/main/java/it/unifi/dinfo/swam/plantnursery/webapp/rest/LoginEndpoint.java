package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import it.unifi.dinfo.swam.plantnursery.controller.UserController;
import it.unifi.dinfo.swam.plantnursery.model.User;

@Path("login")
public class LoginEndpoint {
	
	@Inject
	private UserController userController;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user) {
		userController.login(user.getUsername(), user.getPassword());
		
		if(userController.isErrorOccurred()) {
			return Response.status(Status.UNAUTHORIZED).entity(userController.getErrorMessage()).build();
		}
		
		return Response.ok().build();
	}
}
