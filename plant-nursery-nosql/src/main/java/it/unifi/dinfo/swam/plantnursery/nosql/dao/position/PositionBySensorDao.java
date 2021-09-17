package it.unifi.dinfo.swam.plantnursery.nosql.dao.position;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionBySensor;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class PositionBySensorDao extends BaseDao<PositionBySensor> {

	private static final String TABLE_NAME = "positions_by_sensor";

	public void delete(UUID idSensor, UUID idPosition) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("id_sensor").eq(idSensor)
				.and("id").eq(idPosition).build();
		columnTemplate.delete(deleteQuery);
	}

	public List<PositionBySensor> getPositionsBySensor(UUID idSensor) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("id_sensor").eq(idSensor).build();
		Stream<PositionBySensor> positions = columnTemplate.select(query);

		return positions.collect(Collectors.toList());
	}

}
