package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesById;
import jakarta.enterprise.context.Dependent;

@Dependent
public class SpeciesByIdDao extends BaseDao<SpeciesById> {
	
	public void delete(UUID id) {
		columnTemplate.delete(SpeciesById.class, id);
	}
	
	public void update(SpeciesById species) {
		columnTemplate.update(species);
	}
	
	public SpeciesById findById(UUID id) {
		Optional<SpeciesById> species = columnTemplate.find(SpeciesById.class, id);
		
		try {
			return species.get();
		}
		catch(NoSuchElementException e) {
			return null;
		}
	}
}
