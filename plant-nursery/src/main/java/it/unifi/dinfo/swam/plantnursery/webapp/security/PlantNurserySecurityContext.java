package it.unifi.dinfo.swam.plantnursery.webapp.security;

import java.security.Principal;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.SecurityContext;

import it.unifi.dinfo.swam.plantnursery.dao.UserDao;
import it.unifi.dinfo.swam.plantnursery.model.User;

public class PlantNurserySecurityContext implements SecurityContext {
	
	@Inject
	private UserDao userDao;
	private String principalUsername;
	private ContainerRequestContext requestContext;
	
	public PlantNurserySecurityContext(String principalUsername, ContainerRequestContext requestContext) {
		this.principalUsername = principalUsername;
		this.requestContext = requestContext;
	}
	
	@Override
	public Principal getUserPrincipal() {
		return new Principal() {
			
			@Override
			public String getName() {
				return principalUsername;
			}
		};
	}

	@Override
	public boolean isUserInRole(String role) {
		User principal = userDao.getUserByUsername(principalUsername);
		
		if(principal == null)
			return false;
		
		if(!principal.getUserRole().toString().equals(role))
			return false;
		
		return true;
	}

	@Override
	public boolean isSecure() {
		return requestContext.getSecurityContext().isSecure();
	}

	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.BASIC_AUTH;
	}

}
