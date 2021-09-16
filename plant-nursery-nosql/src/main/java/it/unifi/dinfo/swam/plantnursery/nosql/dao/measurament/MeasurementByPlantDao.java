package it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.measurament.MeasurementByPlant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantById;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class MeasurementByPlantDao extends BaseDao<MeasurementByPlant> {

	private static final String TABLE_NAME = "measurements_by_plant";

	public void delete(UUID idPlant, LocalDateTime dateTime, UUID idMeas) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("id_plant").eq(idPlant)
				.and("meas_date").eq(dateTime).and("id").eq(idMeas).build();
		columnTemplate.delete(deleteQuery);
	}

	public List<MeasurementByPlant> getMeasurementsByPlant(PlantById plant, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {

		if (endDateTime.isBefore(startDateTime))
			throw new IllegalArgumentException("endDateTime must be after startDateTime");

		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("id_plant").eq(plant).and("meas_date")
				.gt(startDateTime).and("meas_date").lt(endDateTime).build();

		Stream<MeasurementByPlant> measList = columnTemplate.select(query);

		return measList.collect(Collectors.toList());
	}

}
