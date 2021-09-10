package it.unifi.dinfo.swam.plantnursery.nosql.model;

import com.datastax.oss.driver.api.core.uuid.Uuids;

public class ModelFactory {
	
	private ModelFactory() {
		
	}
	
	public static SpeciesById speciesById() {
		return new SpeciesById(Uuids.timeBased());
	}
	
	public static SpeciesByFilter speciesByFilter() {
		return new SpeciesByFilter(Uuids.timeBased());
	}
}
