package it.unifi.dinfo.swam.plantnursery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
	
	@NotNull
	@Column(unique = true)
	private String username;
	
	@NotNull
	private String password;
	
	@NotNull
	private UserRole userRole;
	
	protected User() {
	}
	
	public User(String uuid) {
		super(uuid);
	}

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

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

}
