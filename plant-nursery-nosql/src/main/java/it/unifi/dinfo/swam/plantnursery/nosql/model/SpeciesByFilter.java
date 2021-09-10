package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.util.UUID;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

@Entity("species_by_filter")
public class SpeciesByFilter {

	@Id("Id")
	private UUID id;

	@Column("name")
	private String name;

	@Column("type")
	private String type;

	public SpeciesByFilter() {
		
	}
	
	public SpeciesByFilter(UUID id) {
		this.id = id;
	}
	
	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
