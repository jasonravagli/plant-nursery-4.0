package it.unifi.dinfo.swam.plantnursery.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "plants")
public class Plant extends BaseEntity {
	
	private Date plantingDate;

	@ManyToOne
	private Species species;
	
	protected Plant() {
	}
	
	public Plant(String uuid) {
		super(uuid);
	}

	public Date getPlantingDate() {
		return plantingDate;
	}

	public void setPlantingDate(Date plantingDate) {
		this.plantingDate = plantingDate;
	}

	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

}
