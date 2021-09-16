package it.unifi.dinfo.swam.plantnursery.nosql.dao.position;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.Position;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionByGrowthPlace;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

public class PositionByGrowthPlaceDao extends BaseDao<PositionByGrowthPlace> {

	private static final String TABLE_NAME = "positions_by_gp";

	public void delete(UUID idGrowthPlace, Boolean free, UUID idPosition) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("growth_place_id")
				.eq(idGrowthPlace).and("free").eq(free).and("id").eq(idPosition).build();
		columnTemplate.delete(deleteQuery);
	}

	public void update(Position oldPosition, PositionByGrowthPlace updatedPosition) {
		delete(oldPosition.getGrowthPlaceId(), oldPosition.isFree(), oldPosition.getId());
		save(updatedPosition);
	}

	public List<PositionByGrowthPlace> getPositionsByGrowthPlace(UUID idGrowthPlace) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("growth_place_id").eq(idGrowthPlace).build();
		Stream<PositionByGrowthPlace> positions = columnTemplate.select(query);

		return positions.collect(Collectors.toList());
	}

	public List<PositionByGrowthPlace> getPlantFreePositionsByGrowthPlace(UUID idGrowthPlace) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("growth_place_id").eq(idGrowthPlace).and("free")
				.eq(true).build();
		Stream<PositionByGrowthPlace> positions = columnTemplate.select(query);

		return positions.collect(Collectors.toList());
	}

}
