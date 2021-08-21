package it.unifi.dinfo.swam.plantnursery.dao;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;

@Dependent
public class GrowthPlaceDao extends BaseDao<GrowthPlace> {

	private static final long serialVersionUID = 3294928687024912826L;

	public GrowthPlaceDao() {
		super(GrowthPlace.class);
	}

	// Only for testing purposes to inject dependencies
	GrowthPlaceDao(EntityManager entityManager) {
		super(GrowthPlace.class, entityManager);
	}

	public List<GrowthPlace> getGrowthPlaces() {
		TypedQuery<GrowthPlace> query = this.entityManager.createQuery("FROM GrowthPlace", GrowthPlace.class);
		return query.getResultList();
	}

	public GrowthPlace getGrowthPlaceByName(String name) {
		TypedQuery<GrowthPlace> query = this.entityManager.createQuery("FROM GrowthPlace WHERE name = :name",
				GrowthPlace.class);
		query.setParameter("name", name);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<GrowthPlace> getGrowthPlacesStartingByName(String name) {
		TypedQuery<GrowthPlace> query = this.entityManager
				.createQuery("FROM GrowthPlace WHERE name LIKE CONCAT(:name, '%')", GrowthPlace.class);
		query.setParameter("name", name);
		return query.getResultList();
	}
}
