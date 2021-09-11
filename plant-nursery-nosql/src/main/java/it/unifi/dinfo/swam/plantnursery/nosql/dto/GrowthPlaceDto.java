package it.unifi.dinfo.swam.plantnursery.nosql.dto;

import java.util.UUID;

public class GrowthPlaceDto {

	private UUID id;
	private String name;
	private GrowthPlaceType type;
	private float latitude;
	private float longitude;
	private int rowsPositions;
	private int columnsPositions;
	
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

	public GrowthPlaceType getType() {
		return type;
	}

	public void setType(GrowthPlaceType type) {
		this.type = type;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public int getRowsPositions() {
		return rowsPositions;
	}

	public void setRowsPositions(int rowPositions) {
		this.rowsPositions = rowPositions;
	}

	public int getColumnsPositions() {
		return columnsPositions;
	}

	public void setColumnsPositions(int colPositions) {
		this.columnsPositions = colPositions;
	}
}
