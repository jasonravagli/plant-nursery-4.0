package it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.GrowthPlaceById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.measurament.MeasurementByGrowthPlace;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class MeasurementByGrowthPlaceDao extends BaseDao<MeasurementByGrowthPlace> {

	private static final String TABLE_NAME = "measurements_by_gp";

	public void delete(UUID idGrowthPlace, LocalDateTime dateTime, UUID idMeas) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("id_growth_place")
				.eq(idGrowthPlace).and("meas_date").eq(dateTime).and("id").eq(idMeas).build();
		columnTemplate.delete(deleteQuery);
	}

	public List<MeasurementByGrowthPlace> getMeasurementsByGrowthPlace(GrowthPlaceById growthPlace,
			LocalDateTime startDateTime, LocalDateTime endDateTime) {

		if (endDateTime.isBefore(startDateTime))
			throw new IllegalArgumentException("endDateTime must be after startDateTime");

		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(growthPlace)
				.and("meas_date").gt(startDateTime).and("meas_date").lt(endDateTime).build();

		Stream<MeasurementByGrowthPlace> measList = columnTemplate.select(query);

		return measList.collect(Collectors.toList());
	}

}
