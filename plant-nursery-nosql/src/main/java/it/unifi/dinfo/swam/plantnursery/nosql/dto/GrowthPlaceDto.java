package it.unifi.dinfo.swam.plantnursery.dto;

public class GrowthPlaceDto extends BaseDto {
	
	private Long id;
	private String name;
	private String type;
	private float latitude;
	private float longitude;
	private int rowsPositions;
	private int columnsPositions;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
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
	public void setRowsPositions(int rowsPositions) {
		this.rowsPositions = rowsPositions;
	}
	public int getColumnsPositions() {
		return columnsPositions;
	}
	public void setColumnsPositions(int columnsPositions) {
		this.columnsPositions = columnsPositions;
	}
	
}
