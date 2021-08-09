package it.unifi.dinfo.swam.plantnursery.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity {
	
	private int rowIndex;
	private int columnIndex;
	
	@ManyToOne
	private GrowthPlace growthPlace;
	
	@ManyToOne
	private Sensor sensor;
	
	@OneToOne
	private Plant plant;
	
	protected Position() {
	}
	
	public Position(String uuid) {
		super(uuid);
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public GrowthPlace getGrowthPlace() {
		return growthPlace;
	}

	public void setGrowthPlace(GrowthPlace growthPlace) {
		this.growthPlace = growthPlace;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}
}
