package it.unifi.dinfo.swam.plantnursery.dao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import it.unifi.dinfo.swam.plantnursery.model.User;

@Dependent
public class UserDao extends BaseDao<User> {

	private static final long serialVersionUID = 2871435613170375944L;

	public UserDao() {
		super(User.class);
	}

	// Only for testing purposes to inject dependencies
	UserDao(EntityManager entityManager) {
		super(User.class, entityManager);
	}

	public User getUserByUsername(String username) {
		try {
			TypedQuery<User> query = entityManager
					.createQuery("FROM User WHERE username = :username", User.class);
			query.setParameter("username", username);

			return query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	public User getUserByCredentials(String username, String password) {
		try {
			TypedQuery<User> query = entityManager
					.createQuery("FROM User WHERE username = :username AND password = :password", User.class);
			query.setParameter("username", username);
			query.setParameter("password", password);

			return query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
}