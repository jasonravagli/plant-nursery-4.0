package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesById;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.nosql.mapping.column.ColumnTemplate;

@Dependent
public class SpeciesByIdDao {
	
	@Inject
    private ColumnTemplate columnTemplate;

    public SpeciesById save(SpeciesById species) {
        return columnTemplate.insert(species);
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
