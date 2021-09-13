package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantBySpecies;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;
import jakarta.nosql.column.ColumnQuery.ColumnFrom;

public class PlantByGrowthPlaceDao extends BaseDao<PlantByGrowthPlace> {
	
	public List<PlantByGrowthPlace> getPlantsByGrowhtPlace(UUID idGrowthPlaces) {
		ColumnQuery query = null;
		ColumnFrom columnFrom = ColumnQuery.select().from("plants_by_gp");
		
		if(idGrowthPlaces == null) {
			query = columnFrom.build();
		}
		else {
			query = columnFrom.where("id_growth_place").eq(idGrowthPlaces).build();
		}
		
		Stream<PlantByGrowthPlace> listPlants = columnTemplate.select(query);
		
		return listPlants.collect(Collectors.toList());
	}
	

	public void delete(UUID idGrowthPlace, LocalDate plantingDate, UUID id) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from("plants_by_gp").where("id_growth_place")
				.eq(idGrowthPlace).and("planting_date").eq(plantingDate).and("id").eq(id).build();
		columnTemplate.delete(deleteQuery);
	}
	
	public PlantByGrowthPlace findById(UUID idGrowthPlace, LocalDate plantingDate, UUID id) {
		ColumnQuery query = ColumnQuery.select().from("plants_by_gp").where("id_growth_place")
				.eq(idGrowthPlace).and("planting_date").eq(plantingDate).and("id").eq(id).build();
		Optional<PlantByGrowthPlace> plants = columnTemplate.singleResult(query);

		try {
			return plants.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

}
