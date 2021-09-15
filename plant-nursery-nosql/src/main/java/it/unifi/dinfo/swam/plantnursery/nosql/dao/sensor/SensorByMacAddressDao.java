package it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor;

import java.util.NoSuchElementException;
import java.util.Optional;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.Sensor;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorByMacAddress;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class SensorByMacAddressDao extends BaseDao<SensorByMacAddress> {
	
	private static String TABLE_NAME = "sensors_by_mac_address";

	public void delete(String macAddress) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("mac_address")
				.eq(macAddress).build();
		columnTemplate.delete(deleteQuery);
	}
	
	public void update(Sensor oldSensor, SensorByMacAddress updatedSensor) {
		delete(oldSensor.getMacAddress());
		save(updatedSensor);
	}

	public SensorByMacAddress getSensorByMacAddress(String macAddress) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("mac_address").eq(macAddress)
				.build();
		Optional<SensorByMacAddress> species = columnTemplate.singleResult(query);

		try {
			return species.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
