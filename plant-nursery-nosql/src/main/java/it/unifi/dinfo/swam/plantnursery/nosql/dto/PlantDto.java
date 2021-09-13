package it.unifi.dinfo.swam.plantnursery.nosql.dto;

import java.time.LocalDate;
import java.util.UUID;

public class PlantDto extends BaseDto{
	
	private UUID id;
	private LocalDate plantingDate;
	private boolean sold;
	private LocalDate saleDate;
	private UUID speciesId;
	private String speciesName;
	private UUID growthPlaceId;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public LocalDate getPlantingDate() {
		return plantingDate;
	}
	public void setPlantingDate(LocalDate plantingDate) {
		this.plantingDate = plantingDate;
	}
	public boolean isSold() {
		return sold;
	}
	public void setSold(boolean sold) {
		this.sold = sold;
	}
	public LocalDate getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}
	public UUID getSpeciesId() {
		return speciesId;
	}
	public void setSpeciesId(UUID speciesId) {
		this.speciesId = speciesId;
	}
	public String getSpeciesName() {
		return speciesName;
	}
	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}
	public UUID getGrowthPlaceId() {
		return growthPlaceId;
	}
	public void setGrowthPlaceId(UUID growthPlaceId) {
		this.growthPlaceId = growthPlaceId;
	}
	
	
}
