package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.util.Set;
import java.util.UUID;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

@Entity("positions_by_gp")
public class PositionsByGrowthPlace extends BaseEntity{
	
	@Column("row_index")
	private int rowIndex;
	
	@Column("col_index")
	private int columnIndex;
	
	@Id("growth_place_id")
	private UUID growthPlaceId;
	
	@Column("growth_place_name")
	private String growthPlaceName;
	
	@Column("id_plant")
	private UUID idPlant;
	
	@Column("id_sensor")
	private Set<UUID> idSensors;
	
	@Column("free")
	private boolean free;

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

	public UUID getGrowthPlaceId() {
		return growthPlaceId;
	}

	public void setGrowthPlaceId(UUID growthPlaceId) {
		this.growthPlaceId = growthPlaceId;
	}

	public String getGrowthPlaceName() {
		return growthPlaceName;
	}

	public void setGrowthPlaceName(String growthPlaceName) {
		this.growthPlaceName = growthPlaceName;
	}

	public UUID getIdPlant() {
		return idPlant;
	}

	public void setIdPlant(UUID idPlant) {
		this.idPlant = idPlant;
	}

	public Set<UUID> getIdSensors() {
		return idSensors;
	}

	public void setIdSensors(Set<UUID> idSensors) {
		this.idSensors = idSensors;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}
}
