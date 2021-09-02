package it.unifi.dinfo.swam.plantnursery.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity {
	
	private int rowIndex;
	private int columnIndex;
	
	@ManyToOne(optional = false)
	private GrowthPlace growthPlace;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Sensor> sensors;
	
	@OneToOne
	private Plant plant;
	
	protected Position() {
		sensors = new ArrayList<Sensor>();
	}
	
	public Position(String uuid) {
		super(uuid);
		
		sensors = new ArrayList<Sensor>();
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
	
	public void addSensor(Sensor sensor) {
		this.sensors.add(sensor);
	}

	public List<Sensor> getSensors() {
		return sensors;
	}
	
	public boolean removeSensor(Sensor sensor) {
		return this.sensors.remove(sensor);
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}
}
