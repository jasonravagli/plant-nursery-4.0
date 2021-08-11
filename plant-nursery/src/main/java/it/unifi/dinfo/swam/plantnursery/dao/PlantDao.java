package it.unifi.dinfo.swam.plantnursery.dao;

import java.util.List;

import it.unifi.dinfo.swam.plantnursery.model.Plant;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class PlantDao extends BaseDao<Plant> {

	public PlantDao() {
		super(Plant.class);
	}
	
	// Only for testing purposes to inject dependencies
	PlantDao(EntityManager entityManager) {
		super(Plant.class, entityManager);
	}

	public List<Plant> getPlants() {
		TypedQuery<Plant> query = this.entityManager.createQuery("FROM Plant", Plant.class);
		return  query.getResultList();
	}

}
