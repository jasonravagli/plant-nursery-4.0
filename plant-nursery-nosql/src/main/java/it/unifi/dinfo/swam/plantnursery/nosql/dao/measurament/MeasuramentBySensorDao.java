package it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.measurament.MeasuramentBySensor;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorById;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class MeasuramentBySensorDao extends BaseDao<MeasuramentBySensor>{

	private static final String TABLE_NAME = "measurements_by_sensor";

	public List<MeasuramentBySensor> getMeasurementsBySensor(SensorById sensor, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {

		if (endDateTime.isBefore(startDateTime))
			throw new IllegalArgumentException("endDateTime must be after startDateTime");

		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("id_sensor").eq(sensor)
				.and("meas_date").gt(startDateTime).and("meas_date").lt(endDateTime).build();
	

		Stream<MeasuramentBySensor> measList = columnTemplate.select(query);

		return measList.collect(Collectors.toList());
	}

}
