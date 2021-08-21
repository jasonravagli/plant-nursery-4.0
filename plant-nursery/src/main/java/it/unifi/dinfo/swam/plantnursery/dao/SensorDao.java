package it.unifi.dinfo.swam.plantnursery.dao;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.Sensor;

@Dependent
public class SensorDao extends BaseDao<Sensor> {

	private static final long serialVersionUID = -7889180159983335750L;

	public SensorDao() {
		super(Sensor.class);
	}

	// Only for testing purposes to inject dependencies
	SensorDao(EntityManager entityManager) {
		super(Sensor.class, entityManager);
	}

	public List<Sensor> getSensors() {
		TypedQuery<Sensor> query = this.entityManager.createQuery("FROM Sensor", Sensor.class);
		return query.getResultList();
	}

	public List<Sensor> getSensorsByGrowthplace(GrowthPlace growthPlace) {
		TypedQuery<Sensor> query = this.entityManager.createQuery(
				"SELECT DISTINCT s FROM Position p JOIN p.sensors s WHERE p.growthPlace = :growthPlace", Sensor.class);
		query.setParameter("growthPlace", growthPlace);
		return query.getResultList();
	}

	public List<Sensor> getSensorsByCompany(String company) {
		TypedQuery<Sensor> query = this.entityManager.createQuery("FROM Sensor WHERE company = :company", Sensor.class);
		query.setParameter("company", company);
		return query.getResultList();
	}

	public Sensor getSensorByMacAddress(String macAddress) {
		TypedQuery<Sensor> query = this.entityManager.createQuery("FROM Sensor WHERE macAddress = :macAddress",
				Sensor.class);
		query.setParameter("macAddress", macAddress);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Sensor> getSensorsByModel(String model) {
		TypedQuery<Sensor> query = this.entityManager.createQuery("FROM Sensor WHERE model = :model", Sensor.class);
		query.setParameter("model", model);
		return query.getResultList();
	}

	public List<Sensor> getDisposedSensors() {
		TypedQuery<Sensor> query = this.entityManager.createQuery("FROM Sensor WHERE disposalDate IS NOT NULL",
				Sensor.class);
		return query.getResultList();
	}

	public List<Sensor> getActiveSensors() {
		TypedQuery<Sensor> query = this.entityManager.createQuery("FROM Sensor WHERE disposalDate IS NULL",
				Sensor.class);
		return query.getResultList();
	}
}
