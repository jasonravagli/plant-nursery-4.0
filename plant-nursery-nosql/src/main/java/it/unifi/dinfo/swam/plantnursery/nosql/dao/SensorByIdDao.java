package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.SensorById;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class SensorByIdDao extends BaseDao<SensorById> {
	
	public void delete(UUID id) {
		columnTemplate.delete(SensorById.class, id);
	}
	
	public void update(SensorById sensor) {
		columnTemplate.update(sensor);
	}

	public SensorById findById(UUID id) {
		Optional<SensorById> sensors = columnTemplate.find(SensorById.class, id);

		try {
			return sensors.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public List<SensorById> getSensors(){
		ColumnQuery query = ColumnQuery.select().from("sensors_by_id").build();
		Stream<SensorById> sensors = columnTemplate.select(query);

		return sensors.collect(Collectors.toList());
	}
}
