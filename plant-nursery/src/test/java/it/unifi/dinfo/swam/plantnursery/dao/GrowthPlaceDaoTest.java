package it.unifi.dinfo.swam.plantnursery.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlaceType;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Species;

class GrowthPlaceDaoTest extends JpaTest {
	
	private GrowthPlaceDao growthPlaceDao;
	
	private final int N_GROWTH_PLACES = 3;
	
	private String name1 = "test-1";
	private GrowthPlaceType type1 = GrowthPlaceType.OPEN_FIELD;
	private float lat1 = 1f;
	private float long1 = 2f;
	private GrowthPlace growthPlace1;
	
	private String name2 = "test-2";
	private GrowthPlaceType type2 = GrowthPlaceType.GREENHOUSE;
	private float lat2 = 3f;
	private float long2 = 4f;
	private GrowthPlace growthPlace2;
	
	private String name3 = "place-1";
	private GrowthPlaceType type3 = GrowthPlaceType.CONTAINER;
	private float lat3 = 5f;
	private float long3 = 6f;
	private GrowthPlace growthPlace3;
	
	@Override
	protected void init() {
		growthPlaceDao = new GrowthPlaceDao(this.entityManager);

		growthPlace1 = ModelFactory.growthPlace();
		growthPlace1.setName(name1);
		growthPlace1.setType(type1);
		growthPlace1.setLatitude(lat1);
		growthPlace1.setLatitude(long1);

		growthPlace2 = ModelFactory.growthPlace();
		growthPlace2.setName(name2);
		growthPlace2.setType(type2);
		growthPlace2.setLatitude(lat2);
		growthPlace2.setLatitude(long2);

		growthPlace3 = ModelFactory.growthPlace();
		growthPlace3.setName(name3);
		growthPlace3.setType(type3);
		growthPlace3.setLatitude(lat3);
		growthPlace3.setLatitude(long3);

		this.entityManager.persist(growthPlace1);
		this.entityManager.persist(growthPlace2);
		this.entityManager.persist(growthPlace3);
	}
	
	// ---- Testing BaseDAO methods

	@Test
	public void testDelete() {
		growthPlaceDao.delete(growthPlace1);
		
		GrowthPlace retrievedPlace = this.entityManager.find(GrowthPlace.class, growthPlace1.getId());
		assertNull(retrievedPlace);
	}

	@Test
	public void testFindById() {
		GrowthPlace retrievedPlace = growthPlaceDao.findById(growthPlace1.getId());
		
		assertEquals(true, this.areGrowthPlacesEqual(retrievedPlace, growthPlace1));
	}
	
	@Test
	public void testFindByIdWithWrongId() {
		GrowthPlace retrievedPlace = growthPlaceDao.findById(0L);
		
		assertNull(retrievedPlace);
	}

	@Test
	public void testSave() {
		Species species = ModelFactory.species();
		this.entityManager.persist(species);
		GrowthPlace growthPlace = ModelFactory.growthPlace();
		growthPlace.setName("test-save");
		growthPlace.setType(GrowthPlaceType.CONTAINER);
		growthPlace.setLatitude(10f);
		growthPlace.setLongitude(20f);
		growthPlaceDao.save(growthPlace);
		
		GrowthPlace retrievedPlace = this.entityManager.find(GrowthPlace.class, growthPlace.getId());

		assertEquals(true, this.areGrowthPlacesEqual(retrievedPlace, growthPlace));
	}

	@Test
	public void testUpdate() {
		growthPlace1.setName("test-update");
		growthPlace1.setType(GrowthPlaceType.TUNNEL);
		growthPlace1.setLatitude(30f);
		growthPlace1.setLongitude(40f);
		growthPlaceDao.update(growthPlace1);
		
		GrowthPlace retrievedPlace = this.entityManager.find(GrowthPlace.class, growthPlace1.getId());

		assertEquals(true, this.areGrowthPlacesEqual(retrievedPlace, growthPlace1));
	}

	// ----- Testing GrowthPlaceDao methods
	
	@Test
	void testGetGrowthPlaces() {
		List<GrowthPlace> growthPlaces = growthPlaceDao.getGrowthPlaces();		
		
		assertEquals(N_GROWTH_PLACES, growthPlaces.size());
		assertEquals(true, growthPlaces.stream().anyMatch(p -> areGrowthPlacesEqual(p, growthPlace1)));
		assertEquals(true, growthPlaces.stream().anyMatch(p -> areGrowthPlacesEqual(p, growthPlace2)));
		assertEquals(true, growthPlaces.stream().anyMatch(p -> areGrowthPlacesEqual(p, growthPlace3)));
	}
	
	@Test
	void testGetGrowthPlaceByName() {
		GrowthPlace retrievedGrowthPlace = growthPlaceDao.getGrowthPlaceByName(name2);
		
		assertEquals(true, areGrowthPlacesEqual(retrievedGrowthPlace, growthPlace2));
	}
	
	@Test
	void testGetGrowthPlacesByNameWhenNameIsNotPresent() {
		String name = "no-name";
		GrowthPlace retrievedGrowthPlace = growthPlaceDao.getGrowthPlaceByName(name);
		
		assertNull(retrievedGrowthPlace);
	}
	
	@Test
	void testGetGrowthPlacesStartingByName() {
		String name = "test";
		List<GrowthPlace> growthPlaces = growthPlaceDao.getGrowthPlacesStartingByName(name);		
		
		assertEquals(2, growthPlaces.size());
		assertEquals(true, growthPlaces.stream().anyMatch(p -> areGrowthPlacesEqual(p, growthPlace1)));
		assertEquals(true, growthPlaces.stream().anyMatch(p -> areGrowthPlacesEqual(p, growthPlace2)));
	}
	
	// Method for field-based equality check between GrowthPlace entities
	private boolean areGrowthPlacesEqual(GrowthPlace place1, GrowthPlace place2) {
		if(place1 == null || place2 == null)
			return false;
		if(!place1.getId().equals(place2.getId()))
			return false;
		if(!place1.getUuid().equals(place2.getUuid()))
			return false;
		if(!place1.getType().equals(place2.getType()))
			return false;
		if(place1.getLatitude() != place2.getLatitude())
			return false;
		if(place1.getLongitude() != place2.getLongitude())
			return false;
		
		return true;
	}

}
