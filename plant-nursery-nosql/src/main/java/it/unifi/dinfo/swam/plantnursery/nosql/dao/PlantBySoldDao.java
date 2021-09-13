package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantBySold;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class PlantBySoldDao extends BaseDao<PlantBySold> {
	
	public List<PlantBySold> getPlantsBySoldFlag(boolean sold) {
		ColumnQuery query = ColumnQuery.select().from("plants_by_sold").where("sold").eq(sold).build();
		
		Stream<PlantBySold> listPlants = columnTemplate.select(query);
		
		return listPlants.collect(Collectors.toList());
	}

	public void delete(UUID idPlant) {
		columnTemplate.delete(PlantBySold.class, idPlant);
		
	}
	
	public PlantBySold findById(UUID id) {
		Optional<PlantBySold> plants = columnTemplate.find(PlantBySold.class, id);
		
		try {
			return plants.get();
		}
		catch(NoSuchElementException e) {
			return null;
		}
	}
}
