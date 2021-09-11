package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnQuery;

import java.util.NoSuchElementException;
import java.util.Optional;

import it.unifi.dinfo.swam.plantnursery.nosql.model.UserByUsername;

@Dependent
public class UserDao extends BaseDao<UserByUsername> {

	public UserByUsername getUserByUsername(String username) {
		ColumnQuery query = ColumnQuery.select().from("user_by_username").where("username").eq(username).build();
		Optional<UserByUsername> user = columnTemplate.singleResult(query);
		
		try {
			return user.get();
		}
		catch(NoSuchElementException e) {
			return null;
		}
	}
	
}