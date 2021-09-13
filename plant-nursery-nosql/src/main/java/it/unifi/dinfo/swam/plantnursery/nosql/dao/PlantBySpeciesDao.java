package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantBySpecies;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;
import jakarta.nosql.column.ColumnQuery.ColumnFrom;

@Dependent
public class PlantBySpeciesDao extends BaseDao<PlantBySpecies> {
	
	public List<PlantBySpecies> getPlantsBySpecies(String speciesName) {
		ColumnQuery query = null;
		ColumnFrom columnFrom = ColumnQuery.select().from("plants_by_species");
		
		if(speciesName == null) {
			query = columnFrom.build();
		}
		else {
			query = columnFrom.where("species_name").eq(speciesName).build();
		}
		
		Stream<PlantBySpecies> listPlants = columnTemplate.select(query);
		
		return listPlants.collect(Collectors.toList());
	}

	public void delete(UUID idSpecies, LocalDate plantingDate, UUID id) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from("plants_by_species").where("species_id")
				.eq(idSpecies).and("planting_date").eq(plantingDate).and("id").eq(id).build();
		columnTemplate.delete(deleteQuery);
	}
	
	public PlantBySpecies findById(UUID idSpecies, LocalDate plantingDate, UUID id) {
		ColumnQuery query = ColumnQuery.select().from("plants_by_species").where("species_id")
				.eq(idSpecies).and("planting_date").eq(plantingDate).and("id").eq(id).build();
		Optional<PlantBySpecies> plants = columnTemplate.singleResult(query);

		try {
			return plants.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
