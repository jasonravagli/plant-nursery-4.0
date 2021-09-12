package it.unifi.dinfo.swam.plantnursery.nosql.dao;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantById;
import jakarta.enterprise.context.Dependent;

@Dependent
public class PlantByIdDao extends BaseDao<PlantById> {
	
	public PlantById findById(UUID id) {
		Optional<PlantById> plants = columnTemplate.find(PlantById.class, id);
		
		try {
			return plants.get();
		}
		catch(NoSuchElementException e) {
			return null;
		}
	}
}
