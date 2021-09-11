package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantsById;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.nosql.mapping.column.ColumnTemplate;

@Dependent
public class PlantsByIdDao {
	
	@Inject
    private ColumnTemplate columnTemplate;
	
	public void save(PlantsById plants) {
        columnTemplate.insert(plants);
    }
    
    public void update(PlantsById plants) {
        columnTemplate.update(plants);
    }
	
	public PlantsById findById(UUID id) {
		Optional<PlantsById> plants = columnTemplate.find(PlantsById.class, id);
		
		try {
			return plants.get();
		}
		catch(NoSuchElementException e) {
			return null;
		}
	}
}
