package it.unifi.dinfo.swam.plantnursery.dao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;

import it.unifi.dinfo.swam.plantnursery.model.Species;

@Dependent
public class SpeciesDao extends BaseDao<Species> {
	
	public SpeciesDao() {
		super(Species.class);
	}

	// Only for testing purposes to inject dependencies
	SpeciesDao(EntityManager entityManager) {
		super(Species.class, entityManager);
	}
}
