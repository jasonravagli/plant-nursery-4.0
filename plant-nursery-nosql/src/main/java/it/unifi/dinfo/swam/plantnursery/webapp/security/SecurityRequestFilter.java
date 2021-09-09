package it.unifi.dinfo.swam.plantnursery.webapp.security;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import it.unifi.dinfo.swam.plantnursery.model.User;
import it.unifi.dinfo.swam.plantnursery.sql.dao.SqlUserDao;

//@AuthenticationBinding
//@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityRequestFilter implements ContainerRequestFilter {

	@Inject
	private SqlUserDao userDao;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader == null || authorizationHeader.isEmpty()) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Request is not authorized")
					.type("text/plain").build());
		}

		if (!authorizationHeader.startsWith("Basic") && !authorizationHeader.startsWith("BASIC")) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Request is not authorized")
					.type("text/plain").build());
		}

		String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
		byte[] bytes = Base64.getDecoder().decode(base64Credentials);
		String credentials = new String(bytes, Charset.forName("UTF-8"));
		// Credentials username:password
		String[] tmp = credentials.split(":", 2);
		String username = tmp[0];
		String password = tmp[1];

		// Authenticate the User
		User user = userDao.getUserByCredentials(username, password);

		// Authentication failed
		if (user == null) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User authentication failed")
					.type("text/plain").build());
		}

		requestContext.setSecurityContext(new PlantNurserySecurityContext(username, requestContext));
	}

}
