package it.unifi.dinfo.swam.plantnursery.nosql.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class MeasuramentDto extends BaseDto{

	private UUID id;
	private LocalDateTime measuramentDate;
	private float value;
	private String unit;
	private String type;
	private UUID idSensor;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public LocalDateTime getMeasuramentDate() {
		return measuramentDate;
	}
	public void setMeasuramentDate(LocalDateTime measuramentDate) {
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
}
