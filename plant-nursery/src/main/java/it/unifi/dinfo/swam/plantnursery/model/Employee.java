package it.unifi.dinfo.swam.plantnursery.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{

	private String email;
	private String password;
	
	protected Employee() {
	}
	
	public Employee(String uuid) {
		super(uuid);
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
