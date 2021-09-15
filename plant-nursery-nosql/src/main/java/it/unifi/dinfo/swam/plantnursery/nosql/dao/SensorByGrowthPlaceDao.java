package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.Sensor;
import it.unifi.dinfo.swam.plantnursery.nosql.model.SensorByGrowthPlace;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;
import jakarta.nosql.mapping.PreparedStatement;

@Dependent
public class SensorByGrowthPlaceDao extends BaseDao<SensorByGrowthPlace> {
	
	private static String TABLE_NAME = "sensors_by_gp";

	public void delete(UUID idGrowthPlace, String company, String model, UUID idSensor) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("growth_place")
				.eq(idGrowthPlace).and("id").eq(idSensor).and("company").eq(company).and("model").eq(model).build();
		columnTemplate.delete(deleteQuery);
	}
	
	public void update(Sensor oldSensor, SensorByGrowthPlace updatedSensor) {
		delete(oldSensor.getIdGrowthPlace(), oldSensor.getCompany(), oldSensor.getModel(), oldSensor.getId());
		save(updatedSensor);
	}

	public List<SensorByGrowthPlace> getSensorsByGp(UUID idGrowthPlace) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace)
				.build();
		Stream<SensorByGrowthPlace> sensors = columnTemplate.select(query);

		return sensors.collect(Collectors.toList());
	}

	public List<SensorByGrowthPlace> getSensorsByGpAndCompany(UUID idGrowthPlace, String company) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace)
				.and("company").eq(company).build();
		Stream<SensorByGrowthPlace> sensors = columnTemplate.select(query);

		return sensors.collect(Collectors.toList());
	}

	public List<SensorByGrowthPlace> getSensorsByGpAndCompanyAndModel(UUID idGrowthPlace, String company, String model) {
		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace)
				.and("company").eq(company).and("model").eq(model).build();
		Stream<SensorByGrowthPlace> sensors = columnTemplate.select(query);

		return sensors.collect(Collectors.toList());
	}

	public List<SensorByGrowthPlace> getSensorsByGpAndModel(UUID idGrowthPlace, String model) {
		PreparedStatement stat = columnTemplate.prepare(
				"SELECT * FROM " + TABLE_NAME + " WHERE id_growth_place = :id_growth_place AND model = :model ALLOW FILTERING");
		stat = stat.bind("id_growth_place", idGrowthPlace);
		stat = stat.bind("model", model);

		Stream<SensorByGrowthPlace> sensors = stat.getResult();

		return sensors.collect(Collectors.toList());
	}
}
