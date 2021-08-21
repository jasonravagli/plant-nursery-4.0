package it.unifi.dinfo.swam.plantnursery.dao;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import it.unifi.dinfo.swam.plantnursery.model.PlantType;
import it.unifi.dinfo.swam.plantnursery.model.Species;

@Dependent
public class SpeciesDao extends BaseDao<Species> {

	private static final long serialVersionUID = -2055927676326467635L;

	public SpeciesDao() {
		super(Species.class);
	}

	// Only for testing purposes to inject dependencies
	SpeciesDao(EntityManager entityManager) {
		super(Species.class, entityManager);
	}

	public List<Species> getSpecies() {
		TypedQuery<Species> query = this.entityManager.createQuery("FROM Species", Species.class);
		return query.getResultList();
	}
	
	public Species getSpeciesByName(String name) {
		TypedQuery<Species> query = this.entityManager.createQuery("FROM Species WHERE name = :name",
				Species.class);
		query.setParameter("name", name);
		
		try {
			return query.getSingleResult();
		}
		catch(NoResultException e) {
			return null;
		}
	}

	public List<Species> getSpeciesByPlantType(PlantType plantType) {
		TypedQuery<Species> query = this.entityManager.createQuery("FROM Species WHERE type = :type",
				Species.class);
		query.setParameter("type", plantType);
		return query.getResultList();
	}

	public List<Species> getSpeciesStartingByName(String prefixName) {
		TypedQuery<Species> query = this.entityManager.createQuery("FROM Species WHERE name LIKE CONCAT(:name, '%')",
				Species.class);
		query.setParameter("name", prefixName);
		return query.getResultList();
	}
}
