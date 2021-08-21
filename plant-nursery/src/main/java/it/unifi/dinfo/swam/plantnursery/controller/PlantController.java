package it.unifi.dinfo.swam.plantnursery.controller;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.GrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.dao.PlantDao;
import it.unifi.dinfo.swam.plantnursery.dao.PositionDao;
import it.unifi.dinfo.swam.plantnursery.dao.SpeciesDao;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Species;

@RequestScoped
public class PlantController {
	
	@Inject
	private PlantDao plantDao;
	
	@Inject
	private GrowthPlaceDao growthPlaceDao;

	@Inject
	private SpeciesDao speciesDao;
	
	@Inject
	private PositionDao positionDao;
	
	public boolean deletePlant(Long idPlant) {
		Plant plant = plantDao.findById(idPlant);
		
		if(plant == null)
			return false;
		
		Position position = positionDao.getPositionByPlant(plant);
		if(position != null) {
			position.setPlant(null);
			positionDao.update(position);
		}
		
		// TODO Delete measures
		
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
		
		if(growthPlace == null)
			return null;
		
		return plantDao.getPlantsByGrowthPlace(growthPlace);
	}
	
	public List<Plant> getPlantsByPlantingDate(LocalDate dateStart, LocalDate dateEnd){
		if(dateEnd.isBefore(dateStart))
			return null;
		
		return plantDao.getPlantsByPlantingDateRange(dateStart, dateEnd);
	}
	
	public List<Plant> getPlantsBySpecies(Long idSpecies){
		Species species = speciesDao.findById(idSpecies);
		
		if(species == null)
			return null;
		
		return plantDao.getPlantsBySpecies(species);
	}
	
	public List<Plant> getNotSoldPlants() {
		return plantDao.getNotSoldPlants();
	}
	
	public List<Plant> getSoldPlants() {
		return plantDao.getSoldPlants();
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
