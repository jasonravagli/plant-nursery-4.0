package it.unifi.dinfo.swam.plantnursery.dao;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Sensor;

@Dependent
public class PositionDao extends BaseDao<Position> {

	private static final long serialVersionUID = 8088823223207644340L;

	public PositionDao() {
		super(Position.class);
	}

	// Only for testing purposes to inject dependencies
	PositionDao(EntityManager entityManager) {
		super(Position.class, entityManager);
	}

	public List<Position> getPositionsByGrowthPlace(GrowthPlace growthPlace) {
		TypedQuery<Position> query = this.entityManager
				.createQuery("FROM Position p WHERE p.growthPlace = :growthPlace", Position.class);
		query.setParameter("growthPlace", growthPlace);
		return query.getResultList();
	}

	public Position getPositionByPlant(Plant plant) {
		TypedQuery<Position> query = this.entityManager.createQuery("FROM Position WHERE plant = :plant",
				Position.class);
		query.setParameter("plant", plant);

		try {
			return query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public List<Position> getPlantFreePositions() {
		TypedQuery<Position> query = this.entityManager.createQuery("FROM Position WHERE plant = null", Position.class);
		return query.getResultList();
	}

	public List<Position> getPlantFreePositionsByGrowthPlace(GrowthPlace growthPlace) {
		TypedQuery<Position> query = this.entityManager
				.createQuery("FROM Position WHERE growthPlace = :growthPlace AND plant = null", Position.class);
		query.setParameter("growthPlace", growthPlace);
		return query.getResultList();
	}

	public List<Position> getPositionsBySensor(Sensor sensor) {
		TypedQuery<Position> query = this.entityManager
				.createQuery("SELECT p FROM Position p JOIN p.sensors s WHERE s = :sensor", Position.class);
		query.setParameter("sensor", sensor);
		return query.getResultList();
	}

}
