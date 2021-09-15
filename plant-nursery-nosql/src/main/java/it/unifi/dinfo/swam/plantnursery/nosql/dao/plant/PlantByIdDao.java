package it.unifi.dinfo.swam.plantnursery.nosql.dao.plant;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantById;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class PlantByIdDao extends BaseDao<PlantById> {
	
	private static final String TABLE_NAME = "plants_by_id";

	public void delete(UUID id) {
		columnTemplate.delete(PlantById.class, id);
	}
	
	public void update(PlantById plant) {
		columnTemplate.update(plant);
	}
	
	public PlantById findById(UUID id) {
		Optional<PlantById> plants = columnTemplate.find(PlantById.class, id);
		
		try {
			return plants.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public List<PlantById> getPlants(){
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).build();
		Stream<PlantById> plants = columnTemplate.select(query);

		return plants.collect(Collectors.toList());
	}
}
