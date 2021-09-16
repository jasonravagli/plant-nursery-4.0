package it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.measurament.MeasuramentByPlant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantById;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class MeasuramentByPlantDao extends BaseDao<MeasuramentByPlant>{

	private static final String TABLE_NAME = "measurements_by_plant";

	public List<MeasuramentByPlant> getMeasurementsByPlant(PlantById plant, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {

		if (endDateTime.isBefore(startDateTime))
			throw new IllegalArgumentException("endDateTime must be after startDateTime");

		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("id_plant").eq(plant)
				.and("meas_date").gt(startDateTime).and("meas_date").lt(endDateTime).build();
	

		Stream<MeasuramentByPlant> measList = columnTemplate.select(query);

		return measList.collect(Collectors.toList());
	}

}
