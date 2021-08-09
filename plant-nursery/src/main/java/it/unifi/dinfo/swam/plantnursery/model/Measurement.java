package it.unifi.dinfo.swam.plantnursery.model;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "measurements")
public class Measurement extends BaseEntity {

	private Date date;
	private float value;

	@Enumerated(EnumType.STRING)
	private Unit unit;

	@Enumerated(EnumType.STRING)
	private MeasureType type;

	@ManyToOne
	private Plant plant;

	@ManyToOne
	private Sensor sensor;

	@ManyToOne
	private Position position;
	
	protected Measurement() {
	}
	
	public Measurement(String uuid) {
		super(uuid);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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
