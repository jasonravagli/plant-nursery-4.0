package it.unifi.dinfo.swam.plantnursery.nosql.model.measurament;

import java.time.LocalDateTime;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.model.BaseEntity;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

@Entity("measuraments_by_gp")
public class MeasuramentByGrowthPlace extends BaseEntity implements Measurament {
	
	@Id("meas_date")
	private LocalDateTime measDate;
	
	@Column("value")
	private	float value;
	
	@Column("unit")
	private String unit;
	
	@Column("type")
	private String type;
	
	@Column("id_sensor")
	private UUID idSensor;
	
	@Column("id_plant")
	private UUID idPlant;
	
	@Column("id_position")
	private UUID idPosition;
	
	@Id("id_growth_place")
	private UUID idGrowthPlace;

	public LocalDateTime getMeasDate() {
		return measDate;
	}

	public void setMeasDate(LocalDateTime measDate) {
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
