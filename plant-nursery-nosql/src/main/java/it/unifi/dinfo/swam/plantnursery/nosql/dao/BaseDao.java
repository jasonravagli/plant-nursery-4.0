package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import it.unifi.dinfo.swam.plantnursery.nosql.model.BaseEntity;
import jakarta.inject.Inject;
import jakarta.nosql.mapping.column.ColumnTemplate;

public abstract class BaseDao<T extends BaseEntity> {
	
	@Inject
    protected ColumnTemplate columnTemplate;
	
	public void save(T species) {
        columnTemplate.insert(species);
    }
    
    public void update(T species) {
        columnTemplate.update(species);
    }
}
