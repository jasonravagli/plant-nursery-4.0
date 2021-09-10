package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.NoSuchElementException;
import java.util.Optional;

import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesByFilter;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.nosql.column.ColumnQuery;
import jakarta.nosql.mapping.column.ColumnTemplate;

@Dependent
public class SpeciesByFilterDao {

	@Inject
	private ColumnTemplate columnTemplate;

	public SpeciesByFilter save(SpeciesByFilter species) {
		return columnTemplate.insert(species);
	}

	public SpeciesByFilter getSpeciesByName(String name) {
		ColumnQuery query = ColumnQuery.select().from("species_by_filter").where("name").eq(name).build();
		Optional<SpeciesByFilter> species = columnTemplate.singleResult(query);
		
		try {
			return species.get();
		}
		catch(NoSuchElementException e) {
			return null;
		}
	}
}
