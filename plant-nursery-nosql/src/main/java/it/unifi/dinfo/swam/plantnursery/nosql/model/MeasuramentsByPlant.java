package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

@Entity("measuraments_by_plant")
public class MeasuramentsByPlant  extends BaseEntity {
	
	@Id("meas_date")
	private LocalDate measDate;
	
	@Column("value")
	private	float value;
	
	@Column("unit")
	private String unit;
	
	@Column("type")
	private String type;
	
	@Column("id_sensor")
	private UUID idSensor;
	
	@Id("id_plant")
	private UUID idPlant;
	
	@Column("id_position")
	private UUID idPosition;
	
	@Column("id_growth_place")
	private UUID idGrowthPlace;

	public LocalDate getMeasDate() {
		return measDate;
	}

	public void setMeasDate(LocalDate measDate) {
		this.measDate = measDate;
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