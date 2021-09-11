package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.List;
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
}
