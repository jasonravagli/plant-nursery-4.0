package it.unifi.dinfo.swam.plantnursery.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "measurements")
public class Measurement extends BaseEntity {
	
	@NotNull
	private LocalDateTime date;
	@NotNull
	private float value;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Unit unit;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private MeasureType type;

	@ManyToOne(optional = true)
	private Plant plant;

	@ManyToOne(optional = false)
	private Sensor sensor;

	@ManyToOne(optional = false)
	private Position position;
	
	protected Measurement() {
	}
	
	public Measurement(String uuid) {
		super(uuid);
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

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public MeasureType getType() {
		return type;
	}

	public void setType(MeasureType type) {
		this.type = type;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
