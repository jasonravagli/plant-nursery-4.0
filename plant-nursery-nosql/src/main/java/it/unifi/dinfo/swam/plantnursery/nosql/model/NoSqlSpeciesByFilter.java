package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.model.PlantType;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

@Entity("species_by_filter")
public class NoSqlSpeciesByFilter {
	
	@Id("id")
	private UUID id;
	
	@Column("uuid")
	private String uuid;
	
	@Column("name")
	private String name;
	
	@Column("type")
	private PlantType type;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PlantType getType() {
		return type;
	}

	public void setType(PlantType type) {
		this.type = type;
	}
	
}
