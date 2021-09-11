package it.unifi.dinfo.swam.plantnursery.nosql.controller;


import it.unifi.dinfo.swam.plantnursery.nosql.dao.UserDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.UserByUsername;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class UserController extends BaseController {
	
	@Inject
	private UserDao userDao;
	
	public boolean findByUsername(String username) {
		this.cleanErrorFields();
		
		UserByUsername user = userDao.getUserByUsername(username);
		
		if(user == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Username not valid");
			return false;
		}
		
		return true;
	}
}