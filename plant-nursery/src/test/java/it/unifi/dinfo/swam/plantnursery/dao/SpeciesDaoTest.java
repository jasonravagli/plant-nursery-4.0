package it.unifi.dinfo.swam.plantnursery.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlaceType;
import it.unifi.dinfo.swam.plantnursery.model.LifeParameter;
import it.unifi.dinfo.swam.plantnursery.model.MeasureType;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.PlantType;
import it.unifi.dinfo.swam.plantnursery.model.Species;
import it.unifi.dinfo.swam.plantnursery.model.Unit;

public class SpeciesDaoTest extends JpaTest {
	
	private SpeciesDao speciesDao;
	
	private final int N_SPECIES = 2;
	
	private String name1 = "name-1";
	private String description1 = "desc-1";
	private PlantType plantType1 = PlantType.FLOWER;
	private GrowthPlaceType gpType1 = GrowthPlaceType.GREENHOUSE;
	private Species species1;
	// Life parameter for species1
	private Unit unit1 = Unit.PH_POINTS;
	private MeasureType measureType1 = MeasureType.SOIL_PH;
	private float rangeStart1 = 4;
	private float rangeEnd1 = 7;
	
	private String name2 = "name-2";
	private String description2 = "desc-2";
	private PlantType plantType2 = PlantType.CONIFER;
	private GrowthPlaceType gpType2 = GrowthPlaceType.OPEN_FIELD;
	private Species species2;
	
	@Override
	protected void init() throws IllegalAccessException {
		speciesDao = new SpeciesDao(this.entityManager);
		
		species1 = ModelFactory.species();
		species1.setName(name1);
		species1.setDescription(description1);
		species1.setType(plantType1);
		species1.setGrowthPlaceTypes(Stream.of(gpType1).collect(Collectors.toSet()));
		LifeParameter lp1 = new LifeParameter();
		lp1.setRangeStart(rangeStart1);
		lp1.setRangeEnd(rangeEnd1);
		lp1.setType(measureType1);
		lp1.setUnit(unit1);
		species1.setLifeParams(Stream.of(lp1).collect(Collectors.toList()));
		
		species2 = ModelFactory.species();
		species2.setName(name2);
		species2.setDescription(description2);
		species2.setType(plantType2);
		species2.setGrowthPlaceTypes(Stream.of(gpType2).collect(Collectors.toSet()));
		
		this.entityManager.persist(species1);
		this.entityManager.persist(species2);
	}
	
	// ---- Testing BaseDAO methods

	@Test
	public void testDelete() {
		speciesDao.delete(species1);
		
		Species retrievedSpecies = this.entityManager.find(Species.class, species1.getId());
		assertNull(retrievedSpecies);
	}

	@Test
	public void testFindById() {
		Species retrievedSpecies = speciesDao.findById(species1.getId());
		
		assertEquals(true, this.areSpeciesEqual(retrievedSpecies, species1));
	}
	
	@Test
	public void testFindByIdWithWrongId() {
		Species retrievedSpecies = speciesDao.findById(0L);
		
		assertNull(retrievedSpecies);
	}

	@Test
	public void testSave() {
		Species species = ModelFactory.species();
		species.setName("new-name");
		species.setDescription("new-desc");
		species.setType(PlantType.AQUATIC);
		species.setGrowthPlaceTypes(Stream.of(GrowthPlaceType.OPEN_FIELD, GrowthPlaceType.CONTAINER).collect(Collectors.toSet()));
		LifeParameter lp1 = new LifeParameter();
		lp1.setRangeStart(20);
		lp1.setRangeEnd(50);
		lp1.setType(MeasureType.AIR_HUMIDITY);
		lp1.setUnit(Unit.PERCENTAGE);
		LifeParameter lp2 = new LifeParameter();
		lp2.setRangeStart(10);
		lp2.setRangeEnd(20);
		lp2.setType(MeasureType.LIGHT);
		lp2.setUnit(Unit.LUX);
		species.setLifeParams(Stream.of(lp1, lp2).collect(Collectors.toList()));
		
		speciesDao.save(species);
		
		Species retrievedSpecies = this.entityManager.find(Species.class, species.getId());

		assertEquals(true, this.areSpeciesEqual(retrievedSpecies, species));
	}

	@Test
	public void testUpdate() {
		species1.setName("name-update");
		species1.setDescription("desc-update");
		species1.setType(PlantType.GRASS);
		species1.setGrowthPlaceTypes(Stream.of(GrowthPlaceType.TUNNEL).collect(Collectors.toSet()));
		species1.setLifeParams(new ArrayList<>());
		
		speciesDao.update(species1);
		
		Species retrievedSpecies = this.entityManager.find(Species.class, species1.getId());

		assertEquals(true, this.areSpeciesEqual(retrievedSpecies, species1));
	}

	// ----- Testing SpeciesDaoTest methods
	
	@Test
	void testGetSpecies() {
		List<Species> listSpecies = speciesDao.getSpecies();		
		
		assertEquals(N_SPECIES, listSpecies.size());
		assertEquals(true, listSpecies.stream().anyMatch(p -> areSpeciesEqual(p, species1)));
		assertEquals(true, listSpecies.stream().anyMatch(p -> areSpeciesEqual(p, species2)));
	}
	
	@Test
	void testGetSpeciesByName() {
		Species retrievedSpecies = speciesDao.getSpeciesByName(name2);
		
		assertEquals(true, areSpeciesEqual(retrievedSpecies, species2));
	}
	
	@Test
	void testGetSpeciesByNameWhenNameIsNotPresent() {
		String name = "no-name";
		Species retrievedSpecies = speciesDao.getSpeciesByName(name);
		
		assertNull(retrievedSpecies);
	}
	
	@Test
	void testGetSpeciesStartingByName() {
		String namePrefix = "name";
		List<Species> listSpecies = speciesDao.getSpeciesStartingByName(namePrefix);
		
		assertEquals(2, listSpecies.size());
		assertEquals(true, listSpecies.stream().anyMatch(p -> areSpeciesEqual(p, species1)));
		assertEquals(true, listSpecies.stream().anyMatch(p -> areSpeciesEqual(p, species2)));
	}
	
	@Test
	void testGetSpeciesByPlantType() {
		List<Species> listSpecies = speciesDao.getSpeciesByPlantType(plantType2);
		
		assertEquals(1, listSpecies.size());
		assertEquals(true, listSpecies.stream().anyMatch(p -> areSpeciesEqual(p, species2)));
	}
	
	@Test
	void testGetFilteredSpeciesWhenNoParamsAreProvided() {
		List<Species> listSpecies = speciesDao.getFilteredSpecies(null, null);
		
		assertEquals(2, listSpecies.size());
		assertEquals(true, listSpecies.stream().anyMatch(p -> areSpeciesEqual(p, species1)));
		assertEquals(true, listSpecies.stream().anyMatch(p -> areSpeciesEqual(p, species2)));
	}
	
	@Test
	void testGetFilteredSpeciesWhenAllParamsAreProvided() {
		String prefixName = "name";
		PlantType filterType =  plantType1;
		List<Species> listSpecies = speciesDao.getFilteredSpecies(filterType, prefixName);
		
		assertEquals(1, listSpecies.size());
		assertEquals(true, listSpecies.stream().anyMatch(p -> areSpeciesEqual(p, species1)));
	}
	
	@Test
	void testGetFilteredSpeciesWhenAllParamsAreProvidedAndNoSpeciesMatch() {
		String prefixName = "name";
		PlantType filterType =  PlantType.AQUATIC;
		List<Species> listSpecies = speciesDao.getFilteredSpecies(filterType, prefixName);
		
		assertEquals(0, listSpecies.size());
	}
	
	// Method for field-based equality check between GrowthPlace entities
	private boolean areSpeciesEqual(Species species1, Species species2) {
		if(species1 == null || species2 == null)
			return false;
		if(!species1.getId().equals(species2.getId()))
			return false;
		if(!species1.getUuid().equals(species2.getUuid()))
			return false;
		if(!species1.getName().equals(species2.getName()))
			return false;
		if(!species1.getDescription().equals(species2.getDescription()))
			return false;
		if(!species1.getType().equals(species2.getType()))
			return false;
		if(!species1.getGrowthPlaceTypes().equals(species2.getGrowthPlaceTypes()))
			return false;
		
		if(species1.getLifeParams().size() != species2.getLifeParams().size())
			return false;
		
		for(int i = 0; i < species1.getLifeParams().size(); i++) {
			LifeParameter lp1 = species1.getLifeParams().get(i);
			LifeParameter lp2 = species2.getLifeParams().get(i);
			
			if(lp1.getRangeStart() != lp2.getRangeStart())
				return false;
			if(lp1.getRangeEnd() != lp2.getRangeEnd())
				return false;
			if(!lp1.getType().equals(lp2.getType()))
				return false;
			if(!lp1.getUnit().equals(lp2.getUnit()))
				return false;
		}
		
		return true;
	}

}
