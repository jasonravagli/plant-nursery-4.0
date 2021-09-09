package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlaceType;
import it.unifi.dinfo.swam.plantnursery.model.PlantType;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

@Entity("species_by_id")
public class NoSqlSpeciesById {
	
	@Id("id")
	private UUID id;
	
	@Column("uuid")
	private String uuid;
	
	@Column("name")
	private String name;
	
	@Column("description")
	private String description;
	
	@Column("type")
	private PlantType type;
	
	@Column("growth_place_types")
	private HashSet<String> growthPlaceTypes;
	
	@Column("life_params")
	private Set<NoSqlLifeParameter> lifeParams;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PlantType getType() {
		return type;
	}

	public void setType(PlantType type) {
		this.type = type;
	}

	public HashSet<String> getGrowthPlaceTypes() {
		return growthPlaceTypes;
	}

	public void setGrowthPlaceTypes(HashSet<String> growthPlaceTypes) {
		this.growthPlaceTypes = growthPlaceTypes;
	}

	public Set<NoSqlLifeParameter> getLifeParams() {
		return lifeParams;
	}

	public void setLifeParams(Set<NoSqlLifeParameter> lifeParams) {
		this.lifeParams = lifeParams;
	}
}
