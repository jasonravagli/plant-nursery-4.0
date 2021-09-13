package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantById;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class PlantByIdDao extends BaseDao<PlantById> {
	
	public PlantById findById(UUID id) {
		ColumnQuery query = ColumnQuery.select().from("plant_by_id").where("id").eq(id)
				.build();
		Optional<PlantById> plants = columnTemplate.singleResult(query);

		try {
			return plants.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public void delete(UUID id) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from("plants_by_id")
				.where("id").eq(id).build();
		columnTemplate.delete(deleteQuery);
	}
}
