package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.time.LocalDate;
import java.util.UUID;

public interface Plant {
	
	public UUID getId();
	
	public void setId(UUID id);
	
	public LocalDate getPlantingDate();

	public void setPlantingDate(LocalDate plantingDate);

	public Boolean isSold();

	public void setSold(Boolean sold);

	public LocalDate getSaleDate();

	public void setSaleDate(LocalDate saleDate);

	public UUID getSpeciesId();

	public void setSpeciesId(UUID speciesId);

	public String getSpeciesName();

	public void setSpeciesName(String speciesName);

	public UUID getIdGrowthPlace();
	
	public void setIdGrowthPlace(UUID idGrowthPlace);
}
