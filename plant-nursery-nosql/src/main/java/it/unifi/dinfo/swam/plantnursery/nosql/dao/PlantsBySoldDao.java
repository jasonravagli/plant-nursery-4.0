package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantsBySold;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.nosql.column.ColumnQuery;
import jakarta.nosql.mapping.column.ColumnTemplate;

@Dependent
public class PlantsBySoldDao {

	@Inject
	private ColumnTemplate columnTemplate;

	public void save(PlantsBySold plants) {
		columnTemplate.insert(plants);
	}
	
	public void update(PlantsBySold plants) {
		columnTemplate.update(plants);
	}
	
	public List<PlantsBySold> getPlantsSold(boolean sold) {
		ColumnQuery query = ColumnQuery.select().from("plants_by_sold").where("sold").eq(sold).build();
		Stream<PlantsBySold> listPlants = columnTemplate.select(query);
		try {
			return listPlants.collect(Collectors.toList());
		}
		catch(NoSuchElementException e) {
			return null;
		}
	}
}
