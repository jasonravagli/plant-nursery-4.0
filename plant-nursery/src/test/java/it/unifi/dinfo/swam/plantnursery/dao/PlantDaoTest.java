package it.unifi.dinfo.swam.plantnursery.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Species;
import it.unifi.dinfo.swam.plantnursery.utils.Utilities;

public class PlantDaoTest extends JpaTest {

	private PlantDao plantDao;

	private final int N_PLANTS = 3;

	private Plant plant1;
	private LocalDate plantingDate1 = LocalDate.parse("2021-08-11");
	private boolean sold1 = true;
	private LocalDate saleDate1 = LocalDate.parse("2021-08-01");
	private Species species1;
	private GrowthPlace growthPlace1;
	private Position position1;
	
	private Plant plant2;
	private LocalDate plantingDate2 = LocalDate.parse("2021-08-10");
	private Species species2;
	private GrowthPlace growthPlace2;
	private Position position2;

	// Plant of same species and growth place as plant1
	private Plant plant3;
	private LocalDate plantingDate3 = LocalDate.parse("2021-08-09");
	private Position position3;

	@Override
	protected void init() {
		plantDao = new PlantDao(this.entityManager);

		species1 = ModelFactory.species();
		plant1 = ModelFactory.plant();
		plant1.setPlantingDate(plantingDate1);
		plant1.setSold(sold1);
		plant1.setSaleDate(saleDate1);
		plant1.setSpecies(species1);
		growthPlace1 = ModelFactory.growthPlace();
		position1 = ModelFactory.position();
		position1.setPlant(plant1);
		position1.setGrowthPlace(growthPlace1);

		species2 = ModelFactory.species();
		plant2 = ModelFactory.plant();
		plant2.setPlantingDate(plantingDate2);
		plant2.setSpecies(species2);
		growthPlace2 = ModelFactory.growthPlace();
		position2 = ModelFactory.position();
		position2.setPlant(plant2);
		position2.setGrowthPlace(growthPlace2);

		plant3 = ModelFactory.plant();
		plant3.setPlantingDate(plantingDate3);
		plant3.setSpecies(species1);
		position3 = ModelFactory.position();
		position3.setPlant(plant3);
		position3.setGrowthPlace(growthPlace1);

		this.entityManager.persist(species1);
		this.entityManager.persist(species2);
		this.entityManager.persist(growthPlace1);
		this.entityManager.persist(growthPlace2);
		this.entityManager.persist(position1);
		this.entityManager.persist(position2);
		this.entityManager.persist(position3);
		this.entityManager.persist(plant1);
		this.entityManager.persist(plant2);
		this.entityManager.persist(plant3);
	}

	// ---- Testing BaseDAO methods

	@Test
	public void testDelete() {
		// Remove plant from its position before delete
		position1.setPlant(null);
		this.entityManager.merge(position1);
		
		plantDao.delete(plant1);
		
		Plant retrievedPlant = this.entityManager.find(Plant.class, plant1.getId());
		assertNull(retrievedPlant);
	}

	@Test
	public void testFindById() {
		Plant plant = plantDao.findById(plant1.getId());
		
		assertEquals(true, this.arePlantsEqual(plant, plant1));
	}
	
	@Test
	public void testFindByIdWithWrongId() {
		Plant plant = plantDao.findById(0L);
		
		assertNull(plant);
	}

	@Test
	public void testSave() {
		Species species = ModelFactory.species();
		this.entityManager.persist(species);
		Plant plant = ModelFactory.plant();
		plant.setPlantingDate(LocalDate.parse("2021-08-13"));
		plant.setSpecies(species);
		plantDao.save(plant);
		
		Plant retrievedPlant = this.entityManager.find(Plant.class, plant.getId());
		assertEquals(true, this.arePlantsEqual(retrievedPlant, plant));
	}

	@Test
	public void testUpdate() {
		LocalDate newPlantingDate = LocalDate.parse("2020-08-11");
		Species newSpecies = ModelFactory.species();
		this.entityManager.persist(newSpecies);

		plant1.setPlantingDate(newPlantingDate);
		plant1.setSpecies(newSpecies);
		plantDao.update(plant1);
		
		Plant retrievedPlant = this.entityManager.find(Plant.class, plant1.getId());
		assertEquals(true, this.arePlantsEqual(retrievedPlant, plant1));
	}

	// ----- Testing PlantDAO methods

	@Test
	public void testGetPlants() {
		List<Plant> plants = plantDao.getPlants();

		assertEquals(N_PLANTS, plants.size());
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant1)));
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant2)));
	}

	@Test
	public void testGetPlantsBySpecies() {
		List<Plant> plants = plantDao.getPlantsBySpecies(species1);

		assertEquals(2, plants.size());
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant1)));
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant3)));
	}

	@Test
	public void testGetPlantsBySpeciesWhenSpeciesDoNotHavePlants() {
		Species species = ModelFactory.species();
		this.entityManager.persist(species);

		List<Plant> plants = plantDao.getPlantsBySpecies(species);

		assertEquals(0, plants.size());
	}

	@Test
	public void testGetPlantsByPlantingDateRange() {
		List<Plant> plants = plantDao.getPlantsByPlantingDateRange(plantingDate3, plantingDate2);

		assertEquals(2, plants.size());
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant2)));
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant3)));
	}

	@Test
	public void testGetPlantsByPlantingDateRangeWithNoPlantsInRange() {
		LocalDate startDate = LocalDate.parse("2021-07-01");
		LocalDate endDate = LocalDate.parse("2021-07-10");
		List<Plant> plants = plantDao.getPlantsByPlantingDateRange(startDate, endDate);

		assertEquals(0, plants.size());
	}

	@Test
	public void testGetPlantsByPlantingDateRangeWithInvalidRange() {
		assertThrows(IllegalArgumentException.class, () -> plantDao.getPlantsByPlantingDateRange(plantingDate2, plantingDate3));
	}
	
	@Test
	public void testGetPlantsByGrowthPlace() {
		List<Plant> plants = plantDao.getPlantsByGrowthPlace(growthPlace1);
		
		assertEquals(2, plants.size());
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant1)));
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant3)));
	}
	
	@Test
	public void testGetNotSoldPlants() {
		List<Plant> plants = plantDao.getNotSoldPlants();
		
		assertEquals(2, plants.size());
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant2)));
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant3)));
	}
	
	@Test
	public void testGetSoldPlants() {
		List<Plant> plants = plantDao.getSoldPlants();
		
		assertEquals(1, plants.size());
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant1)));
	}
	
	@Test
	void testGetFilteredPlantsWhenNoParamsAreProvided() {
		List<Plant> plants = plantDao.getFilteredPlants(null, null, null, null, null);
		
		assertEquals(N_PLANTS, plants.size());
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant1)));
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant2)));
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant3)));
	}
	
	@Test
	void testGetFilteredPlantsWhenAllParamsAreProvided() {
		Boolean sold = true;
		LocalDate dateStart = LocalDate.parse("2021-08-11");
		LocalDate dateEnd = LocalDate.parse("2021-09-11");
		List<Plant> plants = plantDao.getFilteredPlants(growthPlace1, species1, sold, dateStart, dateEnd);
		
		assertEquals(1, plants.size());
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEqual(p, plant1)));
	}
	
	@Test
	void testGetFilteredPlantsWhenAllParamsAreProvidedAndNoPlantMatch() {
		Boolean sold = false;
		LocalDate dateStart = LocalDate.parse("2022-08-11");
		LocalDate dateEnd = LocalDate.parse("2022-09-11");
		List<Plant> plants = plantDao.getFilteredPlants(growthPlace2, species1, sold, dateStart, dateEnd);
		
		assertEquals(0, plants.size());
	}
	
	// Method for field-based equality check between Plant entities
	private boolean arePlantsEqual(Plant plant1, Plant plant2) {
		if(plant1 == null || plant2 == null)
			return false;
		if(!plant1.getId().equals(plant2.getId()))
			return false;
		if(!plant1.getUuid().equals(plant2.getUuid()))
			return false;
		if(!Utilities.areDateEqualsOrBothNull(plant1.getPlantingDate(), plant2.getPlantingDate()))
			return false;
		if(plant1.isSold() != plant2.isSold())
			return false;
		if(!Utilities.areDateEqualsOrBothNull(plant1.getSaleDate(), plant2.getSaleDate()))
			return false;
		if(!plant1.getSpecies().getId().equals(plant2.getSpecies().getId()))
			return false;
		
		return true;
	}
}
