package it.unifi.dinfo.swam.plantnursery.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "plants")
public class Plant extends BaseEntity {

	Date platingDate;

	@ManyToOne
	Species species;
	
	protected Plant() {
	}
	
	public Plant(String uuid) {
		super(uuid);
	}

	public Date getPlatingDate() {
		return platingDate;
	}

	public void setPlantingDate(Date platingDate) {
		this.platingDate = platingDate;
	}

	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

}
