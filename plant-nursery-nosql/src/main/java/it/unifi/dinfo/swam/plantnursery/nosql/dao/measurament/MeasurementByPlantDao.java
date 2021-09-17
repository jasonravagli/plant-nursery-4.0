package it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.measurement.MeasurementByPlant;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class MeasurementByPlantDao extends BaseDao<MeasurementByPlant> {

	private static final String TABLE_NAME = "measurements_by_plant";

	public void delete(UUID idPlant, ZonedDateTime dateTime, UUID idMeas) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("id_plant").eq(idPlant)
				.and("meas_date").eq(dateTime).and("id").eq(idMeas).build();
		columnTemplate.delete(deleteQuery);
	}

	public List<MeasurementByPlant> getMeasurementsByPlant(UUID idPlant, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		ColumnQuery query = null;

		if (startDateTime != null || endDateTime != null) {
			if (startDateTime == null)
				startDateTime = LocalDateTime.of(1970, 1, 1, 0, 0);
			if (endDateTime == null)
				endDateTime = LocalDateTime.now().plusYears(10);

			if (endDateTime.isBefore(startDateTime))
				throw new IllegalArgumentException("endDateTime must be after startDateTime");
			query = ColumnQuery.select().from(TABLE_NAME).where("id_plant").eq(idPlant).and("meas_date")
					.gt(startDateTime).and("meas_date").lt(endDateTime).build();
		}else {
			query = ColumnQuery.select().from(TABLE_NAME).where("id_plant").eq(idPlant).build();
		}

		Stream<MeasurementByPlant> measList = columnTemplate.select(query);

		return measList.collect(Collectors.toList());
	}

}
