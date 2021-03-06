package it.unifi.dinfo.swam.plantnursery.nosql.model.plant;

import java.time.LocalDate;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.model.BaseEntity;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

@Entity("plants_by_filter")
public class PlantByFilter extends BaseEntity implements Plant {
	
	@Id("planting_date")
	private LocalDate plantingDate;
	
	@Id("sold")
	private Boolean sold;
	
	@Column("sale_date")
	private LocalDate saleDate;

	@Id("species_id")
	private UUID SpeciesId;
		
	@Column("species_name")
	private String SpeciesName;
					
	@Id("id_growth_place")
	private UUID IdGrowthPlace;

	public LocalDate getPlantingDate() {
		return plantingDate;
	}

	public void setPlantingDate(LocalDate plantingDate) {
		this.plantingDate = plantingDate;
	}

	public Boolean isSold() {
		return sold;
	}

	public void setSold(Boolean sold) {
		this.sold = sold;
	}

	public LocalDate getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public UUID getSpeciesId() {
		return SpeciesId;
	}

	public void setSpeciesId(UUID speciesId) {
		SpeciesId = speciesId;
	}

	public String getSpeciesName() {
		return SpeciesName;
	}

	public void setSpeciesName(String speciesName) {
		SpeciesName = speciesName;
	}

	public UUID getIdGrowthPlace() {
		return IdGrowthPlace;
	}

	public void setIdGrowthPlace(UUID idGrowthPlace) {
		IdGrowthPlace = idGrowthPlace;
	}
}
