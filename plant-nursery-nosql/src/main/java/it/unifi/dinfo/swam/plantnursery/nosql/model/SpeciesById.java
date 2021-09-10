package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.util.Set;
import java.util.UUID;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

@Entity("species_by_id")
public class SpeciesById {
	
	@Id("id")
	private UUID id;
	
	@Column("name")
	private String name;
	
	@Column("description")
	private String description;
	
	@Column("type")
	private String type;
	
	@Column("growth_place_types")
	private Set<String> growthPlaceTypes;
	
	@Column("life_params")
	private Set<String> lifeParams;
	
	public SpeciesById() {
		
	}
	
	public SpeciesById(UUID id) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<String> getGrowthPlaceTypes() {
		return growthPlaceTypes;
	}

	public void setGrowthPlaceTypes(Set<String> growthPlaceTypes) {
		this.growthPlaceTypes = growthPlaceTypes;
	}

	public Set<String> getLifeParams() {
		return lifeParams;
	}

	public void setLifeParams(Set<String> lifeParams) {
		this.lifeParams = lifeParams;
	}
}
