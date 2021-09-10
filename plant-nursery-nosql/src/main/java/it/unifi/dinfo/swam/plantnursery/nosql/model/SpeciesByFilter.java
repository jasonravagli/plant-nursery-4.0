package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.util.UUID;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Id;

public class SpeciesByFilter {

	@Id("Id")
	private UUID id;

	@Column("uuid")
	private UUID uuid;

	@Column("name")
	private String name;

	@Column("type")
	private String type;

	public SpeciesByFilter() {
		
	}
	
	public SpeciesByFilter(UUID uuid) {
		this.uuid = uuid;
	}
	
	public UUID getId() {
		return id;
	}

	public UUID getUuid() {
		return uuid;
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
