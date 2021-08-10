package it.unifi.dinfo.swam.plantnursery.dao;

import it.unifi.dinfo.swam.plantnursery.model.BaseEntity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public abstract class BaseDao<T extends BaseEntity> implements Serializable {

    private final Class<T> type;

    @PersistenceContext
    protected EntityManager entityManager;

    public BaseDao( Class<T> type ) {
        this.type = type;
    }

    public T findById(Long id ) {
        return entityManager.find( type, id );
    }

    @Transactional
    public void save( T entity ) {
        if(entity.getId() == null)
            entityManager.persist( entity );
        else
            entityManager.merge( entity );
    }

    @Transactional
    public void save(Collection<T> entities ) {
        for(BaseEntity entity: entities)
            this.save( (T) entity );
    }

    public void delete( T entity ) {
        entityManager.remove( entity );
    }

}