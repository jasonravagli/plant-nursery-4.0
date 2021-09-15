package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.GrowthPlaceById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.Position;
import it.unifi.dinfo.swam.plantnursery.nosql.model.PositionByGrowthPlace;
import jakarta.nosql.column.ColumnQuery;

public class PositionByGrowthPlaceDao extends BaseDao<PositionByGrowthPlace> {
	
	private static final String TABLE_NAME = "positions_by_gp";
	
	public List<Position> getPositionsByGrowthPlace(GrowthPlaceById growthPlace) {		
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("growth_place_id").eq(growthPlace).build();
		Stream<Position> positions = columnTemplate.select(query);

		return positions.collect(Collectors.toList());
	}
	
	public List<Position> getPlantFreePositionsByGrowthPlace(GrowthPlaceById growthPlace) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("growth_place_id").eq(growthPlace).and("free").eq(true).build();
		Stream<Position> positions = columnTemplate.select(query);

		return positions.collect(Collectors.toList());
	}

	
	
}
