package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantBySpecies;
import jakarta.enterprise.context.Dependent;
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

	public void delete(UUID idPlant) {
			columnTemplate.delete(PlantBySpecies.class, idPlant);		
	}
	
	public PlantBySpecies findById(UUID id) {
		Optional<PlantBySpecies> plants = columnTemplate.find(PlantBySpecies.class, id);
		
		try {
			return plants.get();
		}
		catch(NoSuchElementException e) {
			return null;
		}
	}
}
