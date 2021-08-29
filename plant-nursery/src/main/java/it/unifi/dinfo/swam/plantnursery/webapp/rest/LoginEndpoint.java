package it.unifi.dinfo.swam.plantnursery.webapp.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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
