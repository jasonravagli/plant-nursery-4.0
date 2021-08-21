package it.unifi.dinfo.swam.plantnursery.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.UserDao;
import it.unifi.dinfo.swam.plantnursery.model.User;

@RequestScoped
public class UserController {
	
	@Inject
	private UserDao userDao;
	
	public boolean login(String username, String password) {
		User user = userDao.getUserByCredentials(username, password);
		
		if(user == null)
			return false;
		
		return true;
	}
}
