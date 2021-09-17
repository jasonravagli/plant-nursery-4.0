package it.unifi.dinfo.swam.plantnursery.nosql.dto;

import java.util.Set;
import java.util.UUID;

public class PositionDto extends BaseDto {

	private UUID id;
	private int rowIndex;
	private int colIndex;
	private UUID growthPlaceId;
	private String growthPlaceName;
	private UUID plantId;
	private Set<UUID> listSensorsId;
	private boolean free;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public UUID getGrowthPlaceId() {
		return growthPlaceId;
	}

	public void setGrowthPlaceId(UUID growthPlaceId) {
		this.growthPlaceId = growthPlaceId;
	}

	public String getGrowthPlaceName() {
		return growthPlaceName;
	}

	public void setGrowthPlaceName(String growthPlaceName) {
		this.growthPlaceName = growthPlaceName;
	}

	public UUID getPlantId() {
		return plantId;
	}

	public void setPlantId(UUID idPlant) {
		this.plantId = idPlant;
	}

	public Set<UUID> getListSensorsId() {
		return listSensorsId;
	}

	public void setListSensorsId(Set<UUID> listSensors) {
		this.listSensorsId = listSensors;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

}
