package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.time.LocalDate;
import java.util.UUID;

public interface Measurament {

	public UUID getId();
	
	public void setId(UUID id);
	
	public LocalDate getMeasDate();

	public void setMeasDate(LocalDate measDate);
	
	public float getValue();

	public void setValue(float value);

	public String getUnit();

	public void setUnit(String unit);

	public String getType();

	public void setType(String type);

	public UUID getIdSensor();

	public void setIdSensor(UUID idSensor);

	public UUID getIdPlant();

	public void setIdPlant(UUID idPlant);

	public UUID getIdPosition();

	public void setIdPosition(UUID idPosition);

	public UUID getIdGrowthPlace();

	public void setIdGrowthPlace(UUID idGrowthPlace);
}
