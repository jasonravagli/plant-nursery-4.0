package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.GrowthPlaceById;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class GrowthPlaceByIdDao extends BaseDao<GrowthPlaceById> {

	public void delete(UUID id) {
		columnTemplate.delete(GrowthPlaceById.class, id);
	}
	
	public void update(GrowthPlaceById sensor) {
		columnTemplate.update(sensor);
	}
	
	public GrowthPlaceById findById(UUID id) {
		Optional<GrowthPlaceById> species = columnTemplate.find(GrowthPlaceById.class, id);

		try {
			return species.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public GrowthPlaceById getGrowthPlaceByName(String name) {
		ColumnQuery query = ColumnQuery.select().from("growth_places_by_id").where("name").eq(name).build();
		Optional<GrowthPlaceById> growthPlace = columnTemplate.singleResult(query);

		try {
			return growthPlace.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public List<GrowthPlaceById> getGrowthPlaces() {
		ColumnQuery query = ColumnQuery.select().from("growth_places_by_id").build();
		Stream<GrowthPlaceById> growthPlaces = columnTemplate.select(query);

		return growthPlaces.collect(Collectors.toList());
	}

	public List<GrowthPlaceById> getGrowthPlacesStartingByName(String prefixName) {
		ColumnQuery query = ColumnQuery.select().from("growth_places_by_id").where("name").like(prefixName + "%")
				.build();
		Stream<GrowthPlaceById> growthPlaces = columnTemplate.select(query);

		return growthPlaces.collect(Collectors.toList());
	}
}
