package it.unifi.dinfo.swam.plantnursery.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Species;
import it.unifi.dinfo.swam.plantnursery.utils.Utilities;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class PlantDaoTest extends JpaTest{
	
	private PlantDao plantDao;
	
	private Plant plant1;
	private String plantingDate1;
	private Species species1;
	
	private Plant plant2;
	private String plantingDate2;
	private Species species2;
	
	@Override
	protected void init() {
		plantDao = new PlantDao(this.entityManager);
		
		plantingDate1 = "2021-08-11";
		species1 = ModelFactory.species();
		plant1 = ModelFactory.plant();
		plant1.setPlantingDate(Utilities.getDateFromString(plantingDate1));
		plant1.setSpecies(species1);
		
		plantingDate2 = "2021-08-10";
		species2 = ModelFactory.species();
		plant2 = ModelFactory.plant();
		plant2.setPlantingDate(Utilities.getDateFromString("2021-08-10"));
		plant2.setSpecies(species2);
		
		this.entityManager.persist(species1);
		this.entityManager.persist(species2);
		this.entityManager.persist(plant1);
		this.entityManager.persist(plant2);
	}
	
	@Test
	public void testSave() {
		Plant plant = ModelFactory.plant();
		plantDao.save(plant);
		
		TypedQuery<Plant> query = this.entityManager.createQuery("FROM Plant where uuid = :uuid", Plant.class);
		query.setParameter("uuid", plant.getUuid());
		
		try {
			Plant retrievedPlant = query.getSingleResult();
		}
		catch(NoResultException e) {
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
		
		TypedQuery<Plant> query = this.entityManager.createQuery("FROM Plant where uuid = :uuid", Plant.class);
		query.setParameter("uuid", plant1.getUuid());
		
		try {
			Plant retrievedPlant = query.getSingleResult();
			assertEquals(newPlantingDate, retrievedPlant.getPlatingDate());
			assertEquals(newSpecies, retrievedPlant.getSpecies());
		}
		catch(NoResultException e) {
			fail();
		}
	}
	
	
	@Test
	public void testGetPlants() {
		List<Plant> plants = plantDao.getPlants();
		
		assertEquals(2, plants.size());
		assertEquals(true, plants.stream().anyMatch(p -> p.equals(plant1)));
		assertEquals(true, plants.stream().anyMatch(p -> p.equals(plant2)));
	}
}
