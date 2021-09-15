package it.unifi.dinfo.swam.plantnursery.nosql.dao.plant;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.BaseDao;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.Plant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantBySpecies;
import jakarta.enterprise.context.Dependent;
import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.column.ColumnQuery;

@Dependent
public class PlantBySpeciesDao extends BaseDao<PlantBySpecies> {

	private static final String TABLE_NAME = "plants_by_species";

	public void delete(UUID idSpecies, LocalDate plantingDate, UUID id) {
		ColumnDeleteQuery deleteQuery = ColumnDeleteQuery.delete().from(TABLE_NAME).where("species_id").eq(idSpecies)
				.and("planting_date").eq(plantingDate).and("id").eq(id).build();
		columnTemplate.delete(deleteQuery);
	}

	public void update(Plant oldPlant, PlantBySpecies updatedPlant) {
		delete(oldPlant.getSpeciesId(), oldPlant.getPlantingDate(), oldPlant.getId());
		save(updatedPlant);
	}

	public List<PlantBySpecies> getPlants(UUID speciesId, LocalDate dateStart, LocalDate dateEnd) {

		ColumnQuery query = null;

		if (dateStart != null || dateEnd != null) {
			if (dateStart == null)
				dateStart = LocalDate.of(1970, 1, 1);
			if (dateEnd == null)
				dateEnd = LocalDate.now().plusYears(10);

			query = ColumnQuery.select().from(TABLE_NAME).where("species_id").eq(speciesId).and("planting_date")
					.gt(dateStart).and("planting_date").lt(dateEnd).build();
		} else {
			query = ColumnQuery.select().from(TABLE_NAME).where("species_id").eq(speciesId).build();
		}

		Stream<PlantBySpecies> listPlants = columnTemplate.select(query);

		return listPlants.collect(Collectors.toList());
	}

	public List<PlantBySpecies> getPlants(UUID speciesId, Boolean sold, LocalDate dateStart, LocalDate dateEnd) {
		
		ColumnQuery query = null;
		
		if (dateStart != null || dateEnd != null) {
			if (dateStart == null)
				dateStart = LocalDate.of(1970, 1, 1);
			if (dateEnd == null)
				dateEnd = LocalDate.now().plusYears(10);

			query = ColumnQuery.select().from(TABLE_NAME).where("species_id").eq(speciesId).and("sold")
					.eq(sold).and("planting_date").gt(dateStart).and("planting_date").lt(dateEnd).build();
		} else {
			query = ColumnQuery.select().from(TABLE_NAME).where("species_id").eq(speciesId).and("sold")
					.eq(sold).build();
		}
		Stream<PlantBySpecies> listPlants = columnTemplate.select(query);

		return listPlants.collect(Collectors.toList());
	}
}
