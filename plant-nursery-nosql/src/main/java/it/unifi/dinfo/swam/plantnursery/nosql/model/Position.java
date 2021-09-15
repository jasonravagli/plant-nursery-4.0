package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.util.Set;
import java.util.UUID;

public interface Position {
	
	public UUID getId();
	
	public void setId(UUID id);
	
	public int getRowIndex();

	public void setRowIndex(int rowIndex);

	public int getColumnIndex();

	public void setColumnIndex(int columnIndex);

	public UUID getGrowthPlaceId();

	public void setGrowthPlaceId(UUID growthPlaceId);

	public String getGrowthPlaceName();

	public void setGrowthPlaceName(String growthPlaceName);

	public UUID getIdPlant();

	public void setIdPlant(UUID idPlant);

	public Set<UUID> getIdSensors();

	public void setIdSensors(Set<UUID> idSensors);

	public boolean isFree();

	public void setFree(boolean free);
}
