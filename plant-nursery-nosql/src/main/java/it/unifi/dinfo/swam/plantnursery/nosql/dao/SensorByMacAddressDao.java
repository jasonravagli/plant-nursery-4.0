package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.NoSuchElementException;
import java.util.Optional;

import it.unifi.dinfo.swam.plantnursery.nosql.model.SensorByMacAddress;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class SensorByMacAddressDao extends BaseDao<SensorByMacAddress> {

	public void delete(String macAddress) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from("species_by_mac_address").where("mac_address")
				.eq(macAddress).build();
		columnTemplate.delete(deleteQuery);
	}

	public SensorByMacAddress getSensorByMacAddress(String macAddress) {
		ColumnQuery query = ColumnQuery.select().from("species_by_mac_address").where("mac_address").eq(macAddress)
				.build();
		Optional<SensorByMacAddress> species = columnTemplate.singleResult(query);

		try {
			return species.get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
