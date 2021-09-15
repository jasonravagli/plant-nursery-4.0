package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.Plant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantBySold;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class PlantBySoldDao extends BaseDao<PlantBySold> {
	
	private static final String TABLE_NAME = "plants_by_sold";
	
	public void delete(Boolean sold, LocalDate plantingDate, UUID id) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("sold")
				.eq(sold).and("planting_date").eq(plantingDate).and("id").eq(id).build();
		columnTemplate.delete(deleteQuery);
	}
	
	public void update(Plant oldPlant, PlantBySold updatedPlant) {
		delete(oldPlant.isSold(), oldPlant.getPlantingDate(), oldPlant.getId());
		save(updatedPlant);
	}
	
	public List<PlantBySold> getPlants(Boolean sold, LocalDate startDate, LocalDate endDate) {
		
		if (startDate == null)
			startDate = LocalDate.of(1970, 1, 1);
		if (endDate == null)
			endDate = LocalDate.now().plusYears(10);
		
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("sold")
				.eq(sold).and("planting_date").gt(startDate).and("planting_date").lt(endDate).build();
		Stream<PlantBySold> plants = columnTemplate.select(query);

		return plants.collect(Collectors.toList());
	}
}
