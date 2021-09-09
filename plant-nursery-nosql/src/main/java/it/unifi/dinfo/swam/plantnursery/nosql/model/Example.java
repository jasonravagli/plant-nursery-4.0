package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.util.Set;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.model.PlantType;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

@Entity("examples")
public class Example {
	
	@Id("name")
	private String name;
	
	@Id("id")
	private Long id;
	
	@Column("type")
	private PlantType type;
	
	@Column("set_strings")
	private Set<String> setTypes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlantType getType() {
		return type;
	}

	public void setType(PlantType type) {
		this.type = type;
	}

	public Set<String> getSetTypes() {
		return setTypes;
	}

	public void setSetStrings(Set<String> setTypes) {
		this.setTypes = setTypes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
