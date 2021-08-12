package it.unifi.dinfo.swam.plantnursery.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Plant;

class GrowthPlaceDaoTest extends JpaTest {
	
	private GrowthPlaceDao growthPlaceDao;
	
	private GrowthPlace growthPlace;
	private String growthPlaceName;
	
	@Override
	protected void init() {
		growthPlaceDao = new GrowthPlaceDao(this.entityManager);
		
		growthPlaceName = "serra";
		growthPlace = ModelFactory.growthPlace();
		growthPlace.setName(growthPlaceName);
		growthPlace.setLatitude(1);
		growthPlace.setLatitude(2);
		
		this.entityManager.persist(growthPlace);
	}
	
	@Test
	public void testSave() {
		GrowthPlace growthPlace = ModelFactory.growthPlace();
		growthPlaceDao.save(growthPlace);
		
		TypedQuery<GrowthPlace> query = this.entityManager.createQuery("FROM GrowthPlaces where uuid = :uuid", GrowthPlace.class);
		query.setParameter("uuid", growthPlace.getUuid());
		
		try {
			GrowthPlace retrieved = query.getSingleResult();
		}
		catch(NoResultException e) {
			fail();
		}
	}
	
	@Test
	void testGetGrowthPlaces() {
		List<GrowthPlace> growthPlaces = growthPlaceDao.getGrowthPlaces();		
		assertEquals(1, growthPlaces.size());
	}

	@Test
	void testFindByLatitudeLongitude() {
		float lat=1, lon=2;
		GrowthPlace growthPlace = growthPlaceDao.findByLatitudeLongitude(lat, lon);
		assertEquals("serra", growthPlace.getName());

	}

}
