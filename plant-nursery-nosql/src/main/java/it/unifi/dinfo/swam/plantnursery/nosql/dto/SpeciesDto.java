package it.unifi.dinfo.swam.plantnursery.nosql.dto;

import java.util.Set;
import java.util.UUID;

public class SpeciesDto extends BaseDto {

	private UUID id;
	private String name;
	private String description;
	private PlantType type;
	private Set<GrowthPlaceType> growthPlaceTypes;
	private Set<LifeParameterDto> lifeParams;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public Set<GrowthPlaceType> getGrowthPlaceTypes() {
		return growthPlaceTypes;
	}

	public void setGrowthPlaceTypes(Set<GrowthPlaceType> growthPlaceTypes) {
		this.growthPlaceTypes = growthPlaceTypes;
	}

	public Set<LifeParameterDto> getLifeParams() {
		return lifeParams;
	}

	public void setLifeParams(Set<LifeParameterDto> lifeParams) {
		this.lifeParams = lifeParams;
	}
}
