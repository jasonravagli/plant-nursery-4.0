package it.unifi.dinfo.swam.plantnursery.dao;

import java.time.LocalDate;
import java.util.List;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Species;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class PlantDao extends BaseDao<Plant> {

	private static final long serialVersionUID = 1859451193977065121L;

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

	public List<Plant> getPlantsBySpecies(Species species) {
		TypedQuery<Plant> query = this.entityManager.createQuery("FROM Plant WHERE species = :species", Plant.class);
		query.setParameter("species", species);
		return query.getResultList();
	}

	public List<Plant> getPlantsByPlantingDateRange(LocalDate dateStart, LocalDate dateEnd) {
		if (dateEnd.isBefore(dateStart))
			throw new IllegalArgumentException("dateEnd must be after dateStart");

		TypedQuery<Plant> query = this.entityManager
				.createQuery("FROM Plant WHERE plantingDate >= :dateStart AND plantingDate <= :dateEnd", Plant.class);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateEnd", dateEnd);
		return query.getResultList();
	}

	public List<Plant> getPlantsByGrowthPlace(GrowthPlace growthPlace) {
		TypedQuery<Plant> query = this.entityManager
				.createQuery("SELECT p.plant FROM Position p WHERE p.growthPlace = :growthPlace", Plant.class);
		query.setParameter("growthPlace", growthPlace);
		return query.getResultList();
	}

	public List<Plant> getNotSoldPlants() {
		TypedQuery<Plant> query = this.entityManager.createQuery("FROM Plant WHERE sold = false", Plant.class);
		return query.getResultList();
	}

	public List<Plant> getSoldPlants() {
		TypedQuery<Plant> query = this.entityManager.createQuery("FROM Plant WHERE sold = true", Plant.class);
		return query.getResultList();
	}

	public List<Plant> getFilteredPlants(GrowthPlace growthPlace, Species species, Boolean sold, LocalDate dateStart,
			LocalDate dateEnd) {
		if (dateStart != null && dateEnd != null && dateEnd.isBefore(dateStart))
			throw new IllegalArgumentException("dateEnd must be after dateStart");
		
		TypedQuery<Plant> query;
		if (growthPlace != null) {
			query = this.entityManager.createQuery(
					"SELECT pl FROM Position pos JOIN pos.plant pl WHERE (pos.growthPlace = :growthPlace) AND (:species is null OR pl.species = :species) AND "
							+ "(:sold is null OR pl.sold = :sold) AND (:dateStart is null OR pl.plantingDate >= :dateStart) AND (:dateEnd is null OR pl.plantingDate <= :dateEnd)",
					Plant.class);
			query.setParameter("growthPlace", growthPlace);
		} else {
			query = this.entityManager.createQuery(
					"FROM Plant WHERE (:species is null OR species = :species) AND (:sold is null OR sold = :sold) AND "
							+ "(:dateStart is null OR plantingDate >= :dateStart) AND (:dateEnd is null OR plantingDate <= :dateEnd)",
					Plant.class);
		}
		query.setParameter("species", species);
		query.setParameter("sold", sold);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateEnd", dateEnd);

		return query.getResultList();
	}

}
