package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.model.Plant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantByGrowthPlace;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;
import jakarta.nosql.mapping.PreparedStatement;

@Dependent
public class PlantByGrowthPlaceDao extends BaseDao<PlantByGrowthPlace> {

	private static final String TABLE_NAME = "plants_by_gp";

	public void delete(UUID idGrowthPlace, LocalDate plantingDate, UUID id) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("id_growth_place")
				.eq(idGrowthPlace).and("planting_date").eq(plantingDate).and("id").eq(id).build();
		columnTemplate.delete(deleteQuery);
	}

	public void update(Plant oldPlant, PlantByGrowthPlace updatedPlant) {
		delete(oldPlant.getIdGrowthPlace(), oldPlant.getPlantingDate(), oldPlant.getId());
		save(updatedPlant);
	}

	public List<PlantByGrowthPlace> getPlantsByGp(UUID idGrowthPlace, LocalDate dateStart, LocalDate dateEnd) {

		if (dateStart == null)
			dateStart = LocalDate.of(1970, 1, 1);
		if (dateEnd == null)
			dateEnd = LocalDate.now().plusYears(10);

		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace)
				.and("planting_date").gt(dateStart).and("planting_date").lt(dateEnd).build();

		Stream<PlantByGrowthPlace> listPlants = columnTemplate.select(query);

		return listPlants.collect(Collectors.toList());
	}

	public List<PlantByGrowthPlace> getPlantsByGpAndSpecies(UUID idGrowthPlace, UUID idSpecies, LocalDate dateStart,
			LocalDate dateEnd) {

		Stream<PlantByGrowthPlace> listPlants = null;

		if (dateStart != null || dateEnd != null) {
			if (dateStart == null)
				dateStart = LocalDate.of(1970, 1, 1);
			if (dateEnd == null)
				dateEnd = LocalDate.now().plusYears(10);

			PreparedStatement stat = columnTemplate.prepare("SELECT * FROM " + TABLE_NAME
					+ " WHERE id_growth_place = @id_growth_place AND species_id = @species_id AND "
					+ "planting_date >= @dateStart AND planting_date <= @dateEnd ALLOW FILTERING");
			stat = stat.bind("id_growth_place", idGrowthPlace);
			stat = stat.bind("species_id", idSpecies);
			stat = stat.bind("dateStart", dateStart);
			stat = stat.bind("dateEnd", dateEnd);

			listPlants = stat.getResult();
		} else {

			ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace)
					.and("species_id").eq(idSpecies).build();

			listPlants = columnTemplate.select(query);
		}

		return listPlants.collect(Collectors.toList());
	}

	public List<PlantByGrowthPlace> getPlantsByGpAndSpeciesAndSold(UUID idGrowthPlace, UUID idSpecies, boolean sold,
			LocalDate dateStart, LocalDate dateEnd) {

		if (dateStart == null)
			dateStart = LocalDate.of(1970, 1, 1);
		if (dateEnd == null)
			dateEnd = LocalDate.now().plusYears(10);

		ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace)
				.and("species_id").eq(idSpecies).and("sold").eq(sold).and("planting_date").gt(dateStart)
				.and("planting_date").lt(dateEnd).build();

		Stream<PlantByGrowthPlace> listPlants = columnTemplate.select(query);

		return listPlants.collect(Collectors.toList());
	}

	public List<PlantByGrowthPlace> getPlantsByGpAndSold(UUID idGrowthPlace, Boolean sold, LocalDate dateStart,
			LocalDate dateEnd) {

		Stream<PlantByGrowthPlace> listPlants = null;

		if (dateStart != null || dateEnd != null) {
			if (dateStart == null)
				dateStart = LocalDate.of(1970, 1, 1);
			if (dateEnd == null)
				dateEnd = LocalDate.now().plusYears(10);

			PreparedStatement stat = columnTemplate.prepare("SELECT * FROM " + TABLE_NAME
					+ " WHERE id_growth_place = @id_growth_place AND sold = @sold AND planting_date >= @dateStart AND "
					+ "planting_date <= @dateEnd ALLOW FILTERING");
			stat = stat.bind("id_growth_place", idGrowthPlace);
			stat = stat.bind("sold", sold);
			stat = stat.bind("dateStart", dateStart);
			stat = stat.bind("dateEnd", dateEnd);

			listPlants = stat.getResult();
		} else {
			ColumnQuery query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace)
					.and("sold").eq(sold).build();
			listPlants = columnTemplate.select(query);
		}

		return listPlants.collect(Collectors.toList());
	}

}
