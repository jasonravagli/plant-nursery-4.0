package it.unifi.dinfo.swam.plantnursery.nosql.dao.plant;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.Plant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantByGrowthPlace;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

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

		ColumnQuery query = null;

		if (dateStart != null || dateEnd != null) {
			if (dateStart == null)
				dateStart = LocalDate.of(1970, 1, 1);
			if (dateEnd == null)
				dateEnd = LocalDate.now().plusYears(10);

			query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace)
					.and("planting_date").gt(dateStart).and("planting_date").lt(dateEnd).build();
		} else {
			query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace).build();
		}

		Stream<PlantByGrowthPlace> listPlants = columnTemplate.select(query);

		return listPlants.collect(Collectors.toList());
	}

	public List<PlantByGrowthPlace> getPlantsByGpAndSpecies(UUID idGrowthPlace, UUID idSpecies, LocalDate dateStart,
			LocalDate dateEnd) {

		ColumnQuery query = null;

		if (dateStart != null || dateEnd != null) {
			if (dateStart == null)
				dateStart = LocalDate.of(1970, 1, 1);
			if (dateEnd == null)
				dateEnd = LocalDate.now().plusYears(10);

			query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace)
					.and("planting_date").gt(dateStart).and("planting_date").lt(dateEnd).and("species_id").eq(idSpecies)
					.build();
		} else {

			query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace).and("species_id")
					.eq(idSpecies).build();
		}

		Stream<PlantByGrowthPlace> listPlants = columnTemplate.select(query);

		return listPlants.collect(Collectors.toList());
	}

	public List<PlantByGrowthPlace> getPlantsByGpAndSold(UUID idGrowthPlace, Boolean sold, LocalDate dateStart,
			LocalDate dateEnd) {

		ColumnQuery query = null;
		
		if (dateStart != null || dateEnd != null) {
			if (dateStart == null)
				dateStart = LocalDate.of(1970, 1, 1);
			if (dateEnd == null)
				dateEnd = LocalDate.now().plusYears(10);

			query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace)
					.and("planting_date").gt(dateStart).and("planting_date").lt(dateEnd).and("sold").eq(sold).build();
		} else {
			query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace)
					.and("sold").eq(sold).build();
		}
		
		Stream<PlantByGrowthPlace> listPlants = columnTemplate.select(query);

		return listPlants.collect(Collectors.toList());
	}

}
