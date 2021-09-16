package it.unifi.dinfo.swam.plantnursery.nosql.dao.position;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionById;

public class PositionByIdDao extends BaseDao<PositionById>{

	public void delete(UUID id) {
		columnTemplate.delete(PositionById.class, id);
	}
	
	public void update(PositionById plant) {
		columnTemplate.update(plant);
	}
	
	public PositionById findById(UUID id) {
		Optional<PositionById> plants = columnTemplate.find(PositionById.class, id);
		
		try {
			return plants.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
