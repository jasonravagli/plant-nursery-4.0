package it.unifi.dinfo.swam.plantnursery.nosql.dao.position;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.Position;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionByPlant;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class PositionByPlantDao extends BaseDao<PositionByPlant> {
	
	private static final String TABLE_NAME = "positions_by_plant";

	public void delete(UUID idPlant, UUID idPosition) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("id_plant")
				.eq(idPlant).and("id").eq(idPosition).build();
		columnTemplate.delete(deleteQuery);
	}

	public void update(Position oldPosition, PositionByPlant updatedPosition) {
		delete(oldPosition.getIdPlant(), oldPosition.getId());
		save(updatedPosition);
	}

	public PositionByPlant getPositionByPlant(UUID idPlant) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("id_plant")
				.eq(idPlant).build();
		Optional<PositionByPlant> plants = columnTemplate.singleResult(query);
		
		try {
			return plants.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
}
