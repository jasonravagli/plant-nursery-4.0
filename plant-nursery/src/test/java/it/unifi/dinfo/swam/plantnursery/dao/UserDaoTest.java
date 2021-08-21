package it.unifi.dinfo.swam.plantnursery.dao;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import it.unifi.dinfo.swam.plantnursery.model.User;
import it.unifi.dinfo.swam.plantnursery.model.UserRole;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;

public class UserDaoTest extends JpaTest {
	private UserDao userDao;

	private String username1 = "user-1";
	private String password1 = "pass-1";
	private UserRole role1 = UserRole.EMPLOYEE;
	private User user1;

	@Override
	protected void init() throws IllegalAccessException {
		userDao = new UserDao(this.entityManager);

		user1 = ModelFactory.user();
		user1.setUsername(username1);
		user1.setPassword(password1);
		user1.setUserRole(role1);

		this.entityManager.persist(user1);
	}

	@Test
	public void testSave() {
		User newUser = ModelFactory.user();
		newUser.setUsername("user-new");
		newUser.setPassword("pass-new");
		newUser.setUserRole(UserRole.EMPLOYEE);
		userDao.save(newUser);
		
		User retrievedUser = this.entityManager.find(User.class, newUser.getId());
		
		assertTrue(areUsersEqual(newUser, retrievedUser));
	}
	
	@Test
	public void testGetUserByUsername() {
		User retrievedUser = userDao.getUserByUsername(username1);
		
		assertTrue(areUsersEqual(user1, retrievedUser));
	}
	
	@Test
	public void testGetUserByUsernameWhenUsernameIsWrong() {
		String username = "user-wrong";
		User retrievedUser = userDao.getUserByUsername(username);
		
		assertNull(retrievedUser);
	}

	@Test
	public void testGetUserByCredentials() {
		User retrievedUser = userDao.getUserByCredentials(username1, password1);
		
		assertTrue(areUsersEqual(user1, retrievedUser));
	}
	
	@Test
	public void testGetUserByCredentialsWhenCredentialsAreWrong() {
		String username = "user-1";
		String password = "pass-wrong";
		User retrievedUser = userDao.getUserByCredentials(username, password);
		
		assertNull(retrievedUser);
	}

	// Method for field-based equality check between User entities
	private boolean areUsersEqual(User user1, User user2) {
		if (user1 == null || user2 == null)
			return false;
		if(!user1.getId().equals(user2.getId()))
			return false;
		if(!user1.getUuid().equals(user2.getUuid()))
			return false;
		if(!user1.getUsername().equals(user2.getUsername()))
			return false;
		if(!user1.getPassword().equals(user2.getPassword()))
			return false;
		if(!user1.getUserRole().equals(user2.getUserRole()))
			return false;

		return true;
	}
}
