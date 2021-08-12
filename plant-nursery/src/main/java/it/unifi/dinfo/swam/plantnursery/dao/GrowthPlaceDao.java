package it.unifi.dinfo.swam.plantnursery.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;

public class GrowthPlaceDao extends BaseDao<GrowthPlace> {

	public GrowthPlaceDao() {
		super(GrowthPlace.class);
	}

	// Only for testing purposes to inject dependencies
	GrowthPlaceDao(EntityManager entityManager) {
		super(GrowthPlace.class, entityManager);
	}
	
	public List<GrowthPlace> getGrowthPlaces() {
		TypedQuery<GrowthPlace> query = this.entityManager.createQuery("FROM GrowthPlaces", GrowthPlace.class);
		return  query.getResultList();
	}
	
	public GrowthPlace findByLatitudeLongitude(float latitude, float longitude) {
        try {
            return (GrowthPlace) entityManager.createQuery("FROM GrowthPlaces WHERE latitude=:latitude AND longitude:=longitude")
            		.setParameter("latitude", latitude).setParameter("longitude", longitude).getSingleResult();
        }
        catch (NoResultException nre){
            return null;
        }
    }
}
