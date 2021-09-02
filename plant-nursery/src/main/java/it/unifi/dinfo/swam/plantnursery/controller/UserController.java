package it.unifi.dinfo.swam.plantnursery.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.UserDao;
import it.unifi.dinfo.swam.plantnursery.model.User;

@RequestScoped
public class UserController extends BaseController{
	
	@Inject
	private UserDao userDao;
	
	public boolean login(String username, String password) {
		this.cleanErrorFields();
		
		User user = userDao.getUserByCredentials(username, password);
		
		if(user == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Username or password not valid");
			return false;
		}
		
		return true;
	}
}
