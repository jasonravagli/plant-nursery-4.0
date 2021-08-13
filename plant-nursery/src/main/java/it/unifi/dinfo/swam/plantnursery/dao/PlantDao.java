package it.unifi.dinfo.swam.plantnursery.dao;

import java.sql.Date;
import java.util.List;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Species;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


@Dependent
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
		return query.getResultList();
	}

	public List<Plant> getPlantsBySpecies(Species species1) {
		TypedQuery<Plant> query = this.entityManager.createQuery("FROM Plant WHERE species = :species", Plant.class);
		query.setParameter("species", species1);
		return query.getResultList();
	}

	public List<Plant> getPlantsByPlantingDateRange(Date dateStart, Date dateEnd) {
		if (dateEnd.before(dateStart))
			throw new IllegalArgumentException("dateEnd must be after dateStart");

		TypedQuery<Plant> query = this.entityManager
				.createQuery("FROM Plant WHERE plantingDate >= :dateStart AND plantingDate <= :dateEnd", Plant.class);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateEnd", dateEnd);
		return query.getResultList();
	}

	public List<Plant> getPlantsByGrowthPlace(GrowthPlace growthPlace) {
		TypedQuery<Plant> query = this.entityManager.createQuery(
				"SELECT pl FROM Position pos JOIN pos.plant pl WHERE pos.growthPlace = :growthPlace", Plant.class);
		query.setParameter("growthPlace", growthPlace);
		return query.getResultList();
	}

}
