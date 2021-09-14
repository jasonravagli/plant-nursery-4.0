package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesByFilter;
import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesById;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;
import jakarta.nosql.column.ColumnQuery.ColumnFrom;
import jakarta.nosql.column.ColumnQuery.ColumnWhere;

@Dependent
public class SpeciesByFilterDao extends BaseDao<SpeciesByFilter> {
	
	private static final String TABLE_NAME = "species_by_filter";

	public void delete(String type, UUID id) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("type")
				.eq(type).and("id").eq(id).build();
		columnTemplate.delete(deleteQuery);
	}
	
	public void update(SpeciesById oldSpecies, SpeciesByFilter updatedSpecies) {
		delete(oldSpecies.getType(), oldSpecies.getId());
		save(updatedSpecies);
	}

	public SpeciesByFilter getSpeciesByName(String name) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("name").eq(name).build();
		Optional<SpeciesByFilter> species = columnTemplate.singleResult(query);

		try {
			return species.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public List<SpeciesByFilter> getFilteredSpecies(String plantType, String prefixName) {
		ColumnQuery query = null;
		ColumnFrom columnFrom = ColumnQuery.select().from(TABLE_NAME);

		if (plantType == null && prefixName == null) {
			query = columnFrom.build();
		} else {
			ColumnWhere columnWhere = null;
			if (plantType != null) {
				columnWhere = columnFrom.where("type").eq(plantType);

				if (prefixName != null) {
					columnWhere.and("name").like(prefixName + "%");
				}
			} else {
				columnWhere = columnFrom.where("name").like(prefixName + "%");
			}
			query = columnWhere.build();
		}

		Stream<SpeciesByFilter> listSpecies = columnTemplate.select(query);

		return listSpecies.collect(Collectors.toList());
	}
}
