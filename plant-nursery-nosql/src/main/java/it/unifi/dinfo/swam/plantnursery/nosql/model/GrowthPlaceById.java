package it.unifi.dinfo.swam.plantnursery.nosql.model;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;

@Entity("growth_places_by_id")
public class GrowthPlaceById extends BaseEntity {

	@Column("name")
	private String name;

	@Column("type")
	private String type;

	@Column("latitude")
	private float latitude;

	@Column("longitude")
	private float longitude;

	@Column("row_positions")
	private int rowPositions;

	@Column("col_positions")
	private int colPositions;

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

	public int getRowPositions() {
		return rowPositions;
	}

	public void setRowPositions(int rowPositions) {
		this.rowPositions = rowPositions;
	}

	public int getColPositions() {
		return colPositions;
	}

	public void setColPositions(int colPositions) {
		this.colPositions = colPositions;
	}
}
