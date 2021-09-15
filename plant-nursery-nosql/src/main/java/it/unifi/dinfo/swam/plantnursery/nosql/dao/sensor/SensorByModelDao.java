package it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.Sensor;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorByModel;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class SensorByModelDao extends BaseDao<SensorByModel> {
	
	private static final String TABLE_NAME = "sensors_by_model";
	
	public void delete(String model, UUID idSensor) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("model")
				.eq(model).and("id").eq(idSensor).build();
		columnTemplate.delete(deleteQuery);	
	}
	
	public void update(Sensor oldSensor, SensorByModel updatedSensor) {
		delete(oldSensor.getModel(), oldSensor.getId());
		save(updatedSensor);
	}
	
	public List<SensorByModel> getSensors(String model) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("model").eq(model)
				.build();
		Stream<SensorByModel> sensors = columnTemplate.select(query);

		return sensors.collect(Collectors.toList());
	}
}
