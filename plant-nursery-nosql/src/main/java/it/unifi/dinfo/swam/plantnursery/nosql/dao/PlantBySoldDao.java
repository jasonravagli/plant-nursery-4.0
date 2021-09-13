package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantBySold;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class PlantBySoldDao extends BaseDao<PlantBySold> {
	
	public List<PlantBySold> getPlantsBySoldFlag(boolean sold) {
		ColumnQuery query = ColumnQuery.select().from("plants_by_sold").where("sold").eq(sold).build();
		
		Stream<PlantBySold> listPlants = columnTemplate.select(query);
		
		return listPlants.collect(Collectors.toList());
	}

	public void delete(boolean sold, LocalDate plantingDate, UUID id) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from("plants_by_sold").where("sold")
				.eq(sold).and("planting_date").eq(plantingDate).and("id").eq(id).build();
		columnTemplate.delete(deleteQuery);
	}
	
	public PlantBySold findById(boolean sold, LocalDate plantingDate, UUID id) {
		ColumnQuery query = ColumnQuery.select().from("plant_by_sold").where("sold")
				.eq(sold).and("planting_date").eq(plantingDate).and("id").eq(id).build();
		Optional<PlantBySold> plants = columnTemplate.singleResult(query);

		try {
			return plants.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
