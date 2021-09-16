package it.unifi.dinfo.swam.plantnursery.nosql.dao.position;

import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.Position;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionBySensor;
import jakarta.nosql.column.ColumnDeleteQuery;

public class PositionBySensorDao extends BaseDao<PositionBySensor>{
	
	private static final String TABLE_NAME = "positions_by_sensor";

	public void delete(UUID idSensor, UUID idPosition) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("id_sensor")
				.eq(idSensor).and("id").eq(idPosition).build();
		columnTemplate.delete(deleteQuery);
	}

	public void update(Position oldPosition, PositionBySensor updatedPosition) {
		delete(oldPosition.getIdPlant(), oldPosition.getId());
		save(updatedPosition);
	}
	
}
