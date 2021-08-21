package it.unifi.dinfo.swam.plantnursery.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "plants")
public class Plant extends BaseEntity {
	
	private LocalDate plantingDate;
	private boolean sold;
	private LocalDate saleDate;

	@ManyToOne(optional = false)
	private Species species;
	
	protected Plant() {
		sold = false;
	}
	
	public Plant(String uuid) {
		super(uuid);
		
		sold = false;
	}

	public LocalDate getPlantingDate() {
		return plantingDate;
	}

	public void setPlantingDate(LocalDate plantingDate) {
		this.plantingDate = plantingDate;
	}

	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
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

}
