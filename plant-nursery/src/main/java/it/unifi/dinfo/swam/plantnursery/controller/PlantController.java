package it.unifi.dinfo.swam.plantnursery.controller;

import java.sql.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.GrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.dao.PlantDao;
import it.unifi.dinfo.swam.plantnursery.dao.SpeciesDao;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Species;

@RequestScoped
public class PlantController {
	
	@Inject
	private PlantDao plantDao;
	
	@Inject
	private GrowthPlaceDao growthPlaceDao;

	@Inject
	private SpeciesDao speciesDao;
	
	public boolean deletePlant(Long idPlant) {
		Plant plant = plantDao.findById(idPlant);
		
		if(plant == null)
			return false;
		
		plantDao.delete(plant);
		return true;
	}
	
	public Plant getPlantById(Long id) {
		return plantDao.findById(id);
	}
	
	public List<Plant> getPlants() {
		return plantDao.getPlants();
	}
	
	public List<Plant> getPlantsByGrowthPlace(Long idGrowthPlace){
		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);
		return plantDao.getPlantsByGrowthPlace(growthPlace);
	}
	
	public List<Plant> getPlantsByPlantingDate(Date dateStart, Date dateEnd){
		return plantDao.getPlantsByPlantingDateRange(dateStart, dateEnd);
	}
	
	public List<Plant> getPlantsBySpecies(Long idSpecies){
		Species species = speciesDao.findById(idSpecies);
		return plantDao.getPlantsBySpecies(species);
	}
	
	public void savePlant(Plant plant) {
		plantDao.save(plant);
	}
	
	public boolean updatePlant(Plant plant) {
		if(plantDao.findById(plant.getId()) == null)
			return false;
		
		plantDao.update(plant);
		return true;
	}
	
}
