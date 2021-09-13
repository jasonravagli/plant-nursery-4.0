package it.unifi.dinfo.swam.plantnursery.nosql.dto;

import java.time.LocalDate;
import java.util.UUID;

public class MeasuramentDto extends BaseDto{

	private UUID id;
	private LocalDate measuramentDate;
	private float value;
	private String unit;
	private String type;
	private UUID idSensor;
	private UUID idPlant;
	private UUID idPosition;
	private UUID idGrowthPlace;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public LocalDate getMeasuramentDate() {
		return measuramentDate;
	}
	public void setMeasuramentDate(LocalDate measuramentDate) {
		this.measuramentDate = measuramentDate;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public UUID getIdSensor() {
		return idSensor;
	}
	public void setIdSensor(UUID idSensor) {
		this.idSensor = idSensor;
	}
	public UUID getIdPlant() {
		return idPlant;
	}
	public void setIdPlant(UUID idPlant) {
		this.idPlant = idPlant;
	}
	public UUID getIdPosition() {
		return idPosition;
	}
	public void setIdPosition(UUID idPosition) {
		this.idPosition = idPosition;
	}
	public UUID getIdGrowthPlace() {
		return idGrowthPlace;
	}
	public void setIdGrowthPlace(UUID idGrowthPlace) {
		this.idGrowthPlace = idGrowthPlace;
	}
	
	
}
