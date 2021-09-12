package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.model.SensorById;
import jakarta.enterprise.context.Dependent;

@Dependent
public class SensorByIdDao extends BaseDao<SensorById> {
	
	public void delete(UUID id) {
		columnTemplate.delete(SensorById.class, id);
	}

	public SensorById findById(UUID id) {
		Optional<SensorById> sensors = columnTemplate.find(SensorById.class, id);

		try {
			return sensors.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
