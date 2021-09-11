package it.unifi.dinfo.swam.plantnursery.nosql.model;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;

@Entity("user_by_username")
public class UserByUsername extends BaseEntity{
	
	@Column("username")
	private String username;
	
	@Column("password")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
