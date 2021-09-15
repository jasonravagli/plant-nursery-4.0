package it.unifi.dinfo.swam.plantnursery.nosql.dao.plant;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.Plant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantByFilter;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class PlantByFilterDao extends BaseDao<PlantByFilter> {

	private static final String TABLE_NAME = "plants_by_filter";

	public void delete(UUID idGrowthPlace, UUID idSpecies, Boolean sold, LocalDate plantingDate, UUID id) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("id_growth_place")
				.eq(idGrowthPlace).and("species_id").eq(idSpecies).and("sold").eq(sold).and("planting_date")
				.eq(plantingDate).and("id").eq(id).build();
		columnTemplate.delete(deleteQuery);
	}

	public void update(Plant oldPlant, PlantByFilter updatedPlant) {
		delete(oldPlant.getIdGrowthPlace(), oldPlant.getSpeciesId(), oldPlant.isSold(), oldPlant.getPlantingDate(),
				oldPlant.getId());
		save(updatedPlant);
	}

	public List<PlantByFilter> getPlants(UUID idGrowthPlace, UUID idSpecies, Boolean sold, LocalDate dateStart,
			LocalDate dateEnd) {

		ColumnQuery query = null;

		if (dateStart != null || dateEnd != null) {
			if (dateStart == null)
				dateStart = LocalDate.of(1970, 1, 1);
			if (dateEnd == null)
				dateEnd = LocalDate.now().plusYears(10);

			query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace)
					.and("planting_date").gt(dateStart).and("planting_date").lt(dateEnd).and("species_id").eq(idSpecies)
					.and("sold").eq(sold).build();
		} else {
			query = ColumnQuery.select().from(TABLE_NAME).where("id_growth_place").eq(idGrowthPlace).and("species_id")
					.eq(idSpecies).and("sold").eq(sold).build();
		}

		Stream<PlantByFilter> listPlants = columnTemplate.select(query);

		return listPlants.collect(Collectors.toList());
	}
}
