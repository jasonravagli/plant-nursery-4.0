package it.unifi.dinfo.swam.plantnursery.nosql.dto;

public class SpeciesBaseInfoDto extends BaseDto {
	private Long id;
	private String name;
	private String plantType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlantType() {
		return plantType;
	}

	public void setPlantType(String plantType) {
		this.plantType = plantType;
	}
}
