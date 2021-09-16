package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.List;

import it.unifi.dinfo.swam.plantnursery.nosql.model.BaseEntity;
import jakarta.inject.Inject;
import jakarta.nosql.mapping.column.ColumnTemplate;

public abstract class BaseDao<T extends BaseEntity> {
	
	@Inject
    protected ColumnTemplate columnTemplate;
	
	public void save(T entity) {
        columnTemplate.insert(entity);
    }
	
	public void saveAll(List<T> listEntities) {
		for (T entity : listEntities)
			columnTemplate.insert(entity);
	}
}
