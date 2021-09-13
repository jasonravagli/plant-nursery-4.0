package it.unifi.dinfo.swam.plantnursery.nosql.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.PlantByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.PlantByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.PlantBySoldDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.PlantBySpeciesDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.PlantDto;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.PlantsMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantBySold;
import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantBySpecies;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class PlantsController extends BaseController {

	
	@Inject
	private PlantByGrowthPlaceDao plantByGrowthPlaceDao;
	
	@Inject
	private PlantByIdDao plantByIdDao;

	@Inject
	private PlantBySoldDao plantBySoldDao;
	
	@Inject
	private PlantBySpeciesDao plantBySpeciesDao;
	
	@Inject
	private PlantsMapper plantsMapper;

	public boolean deletePlant(UUID idPlant) {
		this.cleanErrorFields();

		PlantById plant = plantByIdDao.findById(idPlant);

		if (plant == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant does not exists");
			return false;
		}

		plantByIdDao.delete(plant.getId());
		plantByGrowthPlaceDao.delete(plant.getIdGrowthPlace(), plant.getPlantingDate(), plant.getId());
		plantBySoldDao.delete(plant.isSold(), plant.getPlantingDate(), plant.getId());
		plantBySpeciesDao.delete(plant.getSpeciesId(), plant.getPlantingDate(), plant.getId());
		return true;
	}
	
	public PlantDto getPlantById(UUID id) {
		this.cleanErrorFields();
		
		PlantById plant = plantByIdDao.findById(id);
		if(plant == null) {
			return null;
		}
		
		PlantDto plantsDto;
		try {
			plantsDto = plantsMapper.toDto(plant);
		} catch (IllegalArgumentException e) {
			this.setErrorOccurred(true);
			this.setErrorMessage(e.getMessage());
			return null;
		}

		return plantsDto;
	}

	public boolean savePlant(PlantDto plantDto) throws InstantiationException, IllegalAccessException {
		this.cleanErrorFields();

		if (plantDto == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant cannot be null");
			return false;
		}


		UUID id = Uuids.timeBased();
		try {
			PlantById plantId = plantsMapper.toEntity(id,  plantDto, PlantById.class);
			PlantBySold plantSold = plantsMapper.toEntity(id, plantDto, PlantBySold.class);
			PlantBySpecies plantSpecies = plantsMapper.toEntity(id, plantDto, PlantBySpecies.class);
			PlantByGrowthPlace plantGP = plantsMapper.toEntity(id,  plantDto, PlantByGrowthPlace.class);
			

			plantByIdDao.save(plantId);
			plantBySoldDao.save(plantSold);
			plantBySpeciesDao.save(plantSpecies);
			plantByGrowthPlaceDao.save(plantGP);
		} catch (IllegalArgumentException e) {
			this.setErrorOccurred(true);
			this.setErrorMessage(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean updatePlant(UUID idPlant, PlantDto plantDto) {
		if (plantDto == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant cannot be null");
			return false;
		}

		PlantById plantId = plantByIdDao.findById(idPlant);
		if (plantId == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant to update does not exists");
			return false;
		}
				
		plantByIdDao.update(plantId);
		
		
		PlantBySold plantSold = plantBySoldDao.findById(idPlant);
		PlantBySpecies plantSpecies = plantBySpeciesDao.findById(idPlant);
		PlantByGrowthPlace plantGP = plantByGrowthPlaceDao.findById(idPlant);
		plantBySoldDao.update(plantSold);
		plantBySpeciesDao.update(plantSpecies);
		
		return true;
	}

	public List<PlantDto> getFilteredPlants(UUID idGrowthPlace, UUID idSpecies, Boolean sold, LocalDate dateStart,
			LocalDate dateEnd) {
//		this.cleanErrorFields();
//
//		GrowthPlace growthPlace = null;
//		if (idGrowthPlace != null) {
//			growthPlace = growthPlaceDao.findById(idGrowthPlace);
//
//			if (growthPlace == null) {
//				this.setErrorOccurred(true);
//				this.setErrorMessage("The growth place does not exists");
//				return null;
//			}
//		}
//
//		Species species = null;
//		if (idGrowthPlace != null) {
//			species = speciesDao.findById(idSpecies);
//
//			if (species == null) {
//				this.setErrorOccurred(true);
//				this.setErrorMessage("The species does not exists");
//				return null;
//			}
//		}
//
		List<PlantDto> plants = null;
//		try {
//			plants = plantMapper.toDto(plantDao.getFilteredPlants(growthPlace, species, sold, dateStart, dateEnd));
//		} catch (IllegalArgumentException e) {
//			this.setErrorOccurred(true);
//			this.setErrorMessage(e.getMessage());
//			return null;
//		}
//
		return plants;
}

}
