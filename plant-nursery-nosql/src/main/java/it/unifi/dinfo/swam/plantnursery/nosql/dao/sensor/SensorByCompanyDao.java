package it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.Sensor;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorByCompany;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class SensorByCompanyDao extends BaseDao<SensorByCompany> {
	
	private static String TABLE_NAME = "sensors_by_company";
	
	public void delete(String company, String model, UUID idSensor) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("company")
				.eq(company).and("model").eq(model).and("id").eq(idSensor).build();
		columnTemplate.delete(deleteQuery);
	}
	
	public void update(Sensor oldSensor, SensorByCompany updatedSensor) {
		delete(oldSensor.getCompany(), oldSensor.getModel(), oldSensor.getId());
		save(updatedSensor);
	}
	
	public List<SensorByCompany> getSensors(String company) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("company").eq(company)
				.build();
		Stream<SensorByCompany> sensors = columnTemplate.select(query);

		return sensors.collect(Collectors.toList());
	}

	public List<SensorByCompany> getSensors(String company, String model) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("company").eq(company)
				.and("model").eq(model).build();
		Stream<SensorByCompany> sensors = columnTemplate.select(query);

		return sensors.collect(Collectors.toList());
	}
	
	public SensorByCompany getSensor(String company, String model, UUID id) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("company").eq(company)
				.and("model").eq(model).and("id").eq(id).build();
		Optional<SensorByCompany> sensor = columnTemplate.singleResult(query);

		try {
			return sensor.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
