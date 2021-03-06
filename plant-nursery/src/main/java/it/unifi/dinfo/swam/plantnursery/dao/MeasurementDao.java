package it.unifi.dinfo.swam.plantnursery.dao;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.Measurement;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Sensor;

@Dependent
public class MeasurementDao extends BaseDao<Measurement> {

	private static final long serialVersionUID = 2163126504324193722L;

	public MeasurementDao() {
		super(Measurement.class);
	}

	// Only for testing purposes to inject dependencies
	MeasurementDao(EntityManager entityManager) {
		super(Measurement.class, entityManager);
	}
	
	public List<Measurement> getMeasurementsBySensor(Sensor sensor, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		if (endDateTime.isBefore(startDateTime))
			throw new IllegalArgumentException("endDateTime must be after startDateTime");

		TypedQuery<Measurement> query = this.entityManager.createQuery(
				"FROM Measurement WHERE sensor = :sensor AND date >= :startDateTime AND date <= :endDateTime",
				Measurement.class);
		query.setParameter("sensor", sensor);
		query.setParameter("startDateTime", startDateTime);
		query.setParameter("endDateTime", endDateTime);
		return query.getResultList();
	}

	public List<Measurement> getMeasurementsByPlant(Plant plant, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		if (endDateTime.isBefore(startDateTime))
			throw new IllegalArgumentException("endDateTime must be after startDateTime");

		TypedQuery<Measurement> query = this.entityManager.createQuery(
				"FROM Measurement WHERE plant = :plant AND date >= :startDateTime AND date <= :endDateTime",
				Measurement.class);
		query.setParameter("plant", plant);
		query.setParameter("startDateTime", startDateTime);
		query.setParameter("endDateTime", endDateTime);
		return query.getResultList();
	}

	public List<Measurement> getMeasurementsByGrowthPlace(GrowthPlace growthPlace, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		if (endDateTime.isBefore(startDateTime))
			throw new IllegalArgumentException("endDateTime must be after startDateTime");

		TypedQuery<Measurement> query = this.entityManager.createQuery(
				"FROM Measurement WHERE position.growthPlace = :growthPlace AND date >= :startDateTime AND date <= :endDateTime",
				Measurement.class);
		query.setParameter("growthPlace", growthPlace);
		query.setParameter("startDateTime", startDateTime);
		query.setParameter("endDateTime", endDateTime);
		return query.getResultList();
	}
	
	public List<Measurement> getFilteredMeasurements(Plant plant, Sensor sensor, GrowthPlace growthPlace, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		if (startDateTime != null && endDateTime != null && endDateTime.isBefore(startDateTime))
			throw new IllegalArgumentException("endDateTime must be after startDateTime");

		TypedQuery<Measurement> query = this.entityManager.createQuery(
				"FROM Measurement WHERE (:plant is null OR plant = :plant) AND (:sensor is null OR sensor = :sensor) AND "
				+ "(:growthPlace is null OR position.growthPlace = :growthPlace) AND (:startDateTime is null OR date >= :startDateTime) AND "
				+ "(:endDateTime is null OR date <= :endDateTime)",
				Measurement.class);
		query.setParameter("plant", plant);
		query.setParameter("sensor", sensor);
		query.setParameter("growthPlace", growthPlace);
		query.setParameter("startDateTime", startDateTime);
		query.setParameter("endDateTime", endDateTime);
		return query.getResultList();
	}
}
