package it.unifi.dinfo.swam.plantnursery.nosql.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.unifi.dinfo.swam.plantnursery.utils.LocalDateTimeJacksonDeserializer;
import it.unifi.dinfo.swam.plantnursery.utils.LocalDateTimeJacksonSerializer;

public class MeasurementDto extends BaseDto{

	private UUID id;
	
	@JsonSerialize(using = LocalDateTimeJacksonSerializer.class)
	@JsonDeserialize(using = LocalDateTimeJacksonDeserializer.class)
	private LocalDateTime date;
	
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
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
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
