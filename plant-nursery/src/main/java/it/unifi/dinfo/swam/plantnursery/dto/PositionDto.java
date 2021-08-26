package it.unifi.dinfo.swam.plantnursery.dto;

import java.util.List;

public class PositionDto {
	
	private Long id;
	private int rowIndex;
	private int colIndex;
	private Long growthPlaceId;
	private String growthPlaceName;
	private Long plantId;
	private List<Long> listSensorsId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getGrowthPlaceId() {
		return growthPlaceId;
	}

	public void setGrowthPlaceId(Long growthPlaceId) {
		this.growthPlaceId = growthPlaceId;
	}

	public String getGrowthPlaceName() {
		return growthPlaceName;
	}

	public void setGrowthPlaceName(String growthPlaceName) {
		this.growthPlaceName = growthPlaceName;
	}

	public Long getPlantId() {
		return plantId;
	}

	public void setPlantId(Long plantId) {
		this.plantId = plantId;
	}

	public List<Long> getListSensorsId() {
		return listSensorsId;
	}

	public void setListSensorsId(List<Long> listSensorsId) {
		this.listSensorsId = listSensorsId;
	}
}
