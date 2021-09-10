package it.unifi.dinfo.swam.plantnursery.nosql.dto;

import java.util.UUID;

public class SpeciesBaseInfoDto extends BaseDto {
	
	private UUID id;
	private String name;
	private PlantType plantType;

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

	public PlantType getPlantType() {
		return plantType;
	}

	public void setPlantType(PlantType plantType) {
		this.plantType = plantType;
	}
}
