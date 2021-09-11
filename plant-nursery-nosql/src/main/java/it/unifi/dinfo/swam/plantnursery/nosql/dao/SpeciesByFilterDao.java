package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesByFilter;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;
import jakarta.nosql.column.ColumnQuery.ColumnFrom;
import jakarta.nosql.column.ColumnQuery.ColumnWhere;

@Dependent
public class SpeciesByFilterDao extends BaseDao<SpeciesByFilter> {
	
	public void delete(String type) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from("species_by_filter").where("type").eq(type).build();
		columnTemplate.delete(deleteQuery);
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
	
	public List<SpeciesByFilter> getFilteredSpecies(String plantType, String prefixName) {
		ColumnQuery query = null;
		ColumnFrom columnFrom = ColumnQuery.select().from("species_by_filter");
		
		if(plantType == null && prefixName == null) {
			query = columnFrom.build();
		}
		else {
			ColumnWhere columnWhere = null;
			if(plantType != null) {
				columnWhere = columnFrom.where("type").eq(plantType);
				
				if(prefixName != null) {
					columnWhere.and("name").like(prefixName + "%");
				}
			}
			else {
				columnWhere = columnFrom.where("name").like(prefixName + "%");
			}
			query = columnWhere.build();
		}
		
		Stream<SpeciesByFilter> listSpecies = columnTemplate.select(query);
		
		return listSpecies.collect(Collectors.toList());
	}
}
