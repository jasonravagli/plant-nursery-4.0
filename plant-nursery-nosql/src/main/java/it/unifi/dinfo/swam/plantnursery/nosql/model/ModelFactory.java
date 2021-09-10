package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.util.UUID;

public class ModelFactory {
	
	private ModelFactory() {
		
	}
	
	public static SpeciesById speciesById() {
		return new SpeciesById(UUID.randomUUID());
	}
	
	public static SpeciesByFilter speciesByFilter() {
		return new SpeciesByFilter(UUID.randomUUID());
	}
}
