package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantsBySpecies;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.nosql.column.ColumnQuery;
import jakarta.nosql.column.ColumnQuery.ColumnFrom;
import jakarta.nosql.column.ColumnQuery.ColumnWhere;
import jakarta.nosql.mapping.column.ColumnTemplate;

@Dependent
public class PlantsBySpeciesDao {
	
	@Inject
	private ColumnTemplate columnTemplate;

	public void save(PlantsBySpecies plants) {
		columnTemplate.insert(plants);
	}
	
	public void update(PlantsBySpecies plants) {
		columnTemplate.update(plants);
	}
	
	public List<PlantsBySpecies> getPlantsBySpecies(String speciesName) {
		ColumnQuery query = null;
		ColumnFrom columnFrom = ColumnQuery.select().from("plants_by_species");
		
		if(speciesName == null) {
			query = columnFrom.build();
		}
		else {
			ColumnWhere columnWhere = null;
			if(speciesName != null) {
				columnWhere = columnFrom.where("species_name").eq(speciesName);
			}
			query = columnWhere.build();
		}
		
		Stream<PlantsBySpecies> listPlants = columnTemplate.select(query);
		
		return listPlants.collect(Collectors.toList());
	}
}
