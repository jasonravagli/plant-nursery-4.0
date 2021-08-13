package it.unifi.dinfo.swam.plantnursery.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Species;
import it.unifi.dinfo.swam.plantnursery.utils.Utilities;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class PlantDaoTest extends JpaTest {

	private PlantDao plantDao;

	private final int N_PLANTS = 3;

	private Plant plant1;
	private String plantingDate1 = "2021-08-11";
	private Species species1;
	private GrowthPlace growthPlace1;
	private Position position1;
	
	private Plant plant2;
	private String plantingDate2 = "2021-08-10";
	private Species species2;
	private GrowthPlace growthPlace2;
	private Position position2;

	// Plant of same species and growht place as plant1
	private Plant plant3;
	private String plantingDate3 = "2021-08-09";
	private Position position3;

	@Override
	protected void init() {
		plantDao = new PlantDao(this.entityManager);

		species1 = ModelFactory.species();
		plant1 = ModelFactory.plant();
		plant1.setPlantingDate(Utilities.getDateFromString(plantingDate1));
		plant1.setSpecies(species1);
		growthPlace1 = ModelFactory.growthPlace();
		position1 = ModelFactory.position();
		position1.setPlant(plant1);
		position1.setGrowthPlace(growthPlace1);

		species2 = ModelFactory.species();
		plant2 = ModelFactory.plant();
		plant2.setPlantingDate(Utilities.getDateFromString(plantingDate2));
		plant2.setSpecies(species2);
		growthPlace2 = ModelFactory.growthPlace();
		position2 = ModelFactory.position();
		position2.setPlant(plant2);
		position2.setGrowthPlace(growthPlace2);

		plant3 = ModelFactory.plant();
		plant3.setPlantingDate(Utilities.getDateFromString(plantingDate3));
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

		TypedQuery<Plant> query = this.entityManager.createQuery("FROM Plant where id = :id", Plant.class);
		query.setParameter("id", plant1.getId());

		try {
			Plant retrievedPlant = query.getSingleResult();
			fail();
		} catch (NoResultException e) {
		}
	}

	@Test
	public void testFindById() {
		Plant plant = plantDao.findById(plant1.getId());
		
		assertEquals(true, this.arePlantsEquals(plant, plant1));
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
		plant.setPlantingDate(Utilities.getDateFromString("2021-08-13"));
		plant.setSpecies(species);
		plantDao.save(plant);

		TypedQuery<Plant> query = this.entityManager.createQuery("FROM Plant where id = :id", Plant.class);
		query.setParameter("id", plant.getId());

		try {
			Plant retrievedPlant = query.getSingleResult();
			assertEquals(true, this.arePlantsEquals(retrievedPlant, plant));
		} catch (NoResultException e) {
			fail();
		}
	}

	@Test
	public void testUpdate() {
		Date newPlantingDate = Utilities.getDateFromString("2020-08-11");
		Species newSpecies = ModelFactory.species();
		this.entityManager.persist(newSpecies);

		plant1.setPlantingDate(newPlantingDate);
		plant1.setSpecies(newSpecies);
		plantDao.update(plant1);

		TypedQuery<Plant> query = this.entityManager.createQuery("FROM Plant where id = :id", Plant.class);
		query.setParameter("id", plant1.getId());

		try {
			Plant retrievedPlant = query.getSingleResult();
			assertEquals(true, this.arePlantsEquals(retrievedPlant, plant1));
		} catch (NoResultException e) {
			fail();
		}
	}

	// ----- Testing PlantDAO methods

	@Test
	public void testGetPlants() {
		List<Plant> plants = plantDao.getPlants();

		assertEquals(N_PLANTS, plants.size());
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEquals(p, plant1)));
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEquals(p, plant2)));
	}

	@Test
	public void testGetPlantsBySpecies() {
		List<Plant> plants = plantDao.getPlantsBySpecies(species1);

		assertEquals(2, plants.size());
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEquals(p, plant1)));
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEquals(p, plant3)));
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
		List<Plant> plants = plantDao.getPlantsByPlantingDateRange(Utilities.getDateFromString(plantingDate3),
				Utilities.getDateFromString(plantingDate2));

		assertEquals(2, plants.size());
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEquals(p, plant2)));
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEquals(p, plant3)));
	}

	@Test
	public void testGetPlantsByPlantingDateRangeWithNoPlantsInRange() {
		Date startDate = Utilities.getDateFromString("2021-07-01");
		Date endDate = Utilities.getDateFromString("2021-07-10");
		List<Plant> plants = plantDao.getPlantsByPlantingDateRange(startDate, endDate);

		assertEquals(0, plants.size());
	}

	@Test
	public void testGetPlantsByPlantingDateRangeWithInvalidRange() {
		try {
			List<Plant> plants = plantDao.getPlantsByPlantingDateRange(Utilities.getDateFromString(plantingDate2),
					Utilities.getDateFromString(plantingDate3));
			fail();
		} catch (IllegalArgumentException e) {

		}
	}
	
	@Test
	public void testGetPlantsByGrowthPlace() {
		List<Plant> plants = plantDao.getPlantsByGrowthPlace(growthPlace1);
		
		assertEquals(2, plants.size());
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEquals(p, plant1)));
		assertEquals(true, plants.stream().anyMatch(p -> arePlantsEquals(p, plant3)));
	}
	
	// Method for field-based equality check between Plant entities
	private boolean arePlantsEquals(Plant plant1, Plant plant2) {
		if(plant1 == null || plant2 == null)
			return false;
		if(!plant1.getId().equals(plant2.getId()))
			return false;
		if(!plant1.getUuid().equals(plant2.getUuid()))
			return false;
		if(!plant1.getPlantingDate().equals(plant2.getPlantingDate()))
			return false;
		if(!plant1.getSpecies().getId().equals(plant2.getSpecies().getId()))
			return false;
		
		return true;
	}
}
