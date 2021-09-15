package it.unifi.dinfo.swam.plantnursery.nosql.dto;

import java.util.Set;
import java.util.UUID;

public class PositionDto extends BaseDto{
	
	private UUID id;
	private int rowIndex;
	private int colIndex;
	private UUID idGrowthPlace;
	private String growthPlaceName;
	private UUID idPlant;
	private Set<UUID> idSensors;
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
	public UUID getIdGrowthPlace() {
		return idGrowthPlace;
	}
	public void setIdGrowthPlace(UUID idGrowthPlace) {
		this.idGrowthPlace = idGrowthPlace;
	}
	public String getGrowthPlaceName() {
		return growthPlaceName;
	}
	public void setGrowthPlaceName(String growthPlaceName) {
		this.growthPlaceName = growthPlaceName;
	}
	public UUID getIdPlant() {
		return idPlant;
	}
	public void setIdPlant(UUID idPlant) {
		this.idPlant = idPlant;
	}
	public Set<UUID> getIdSensors() {
		return idSensors;
	}
	public void setIdSensors(Set<UUID> idSensors) {
		this.idSensors = idSensors;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
	
}
