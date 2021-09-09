package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.Optional;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.model.NoSqlSpeciesById;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.nosql.mapping.column.ColumnTemplate;

@RequestScoped
public class NoSqlSpeciesDao {
	
	@Inject
    private ColumnTemplate columnTemplate;

    public NoSqlSpeciesById save(NoSqlSpeciesById species) {
        return columnTemplate.insert(species);
    }
	
	public NoSqlSpeciesById findById(UUID id) {
		Optional<NoSqlSpeciesById> species = columnTemplate.find(NoSqlSpeciesById.class, id);
		return species.get();
	}
}
