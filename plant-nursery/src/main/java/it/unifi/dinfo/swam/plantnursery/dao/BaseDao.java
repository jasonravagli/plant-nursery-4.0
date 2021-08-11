package it.unifi.dinfo.swam.plantnursery.dao;

import it.unifi.dinfo.swam.plantnursery.model.BaseEntity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.io.Serializable;
import java.util.Collection;


public abstract class BaseDao<T extends BaseEntity> implements Serializable {

	private final Class<T> type;

	@PersistenceContext
	protected EntityManager entityManager;

	public BaseDao(Class<T> type) {
		this.type = type;
	}
	
	// Only for testing purposes to inject dependencies
	BaseDao(Class<T> type, EntityManager entityManager) {
		this.type = type;
		this.entityManager = entityManager;
	}

	public T findById(Long id) {
		return entityManager.find(type, id);
	}

	@Transactional
	public void save(T entity) {
		entityManager.persist(entity);
	}

	@Transactional
	public void update(T entity) {
		entityManager.merge(entity);
	}

	@Transactional
	public void delete(T entity) {
		entityManager.remove(entity);
	}

}