package it.unifi.dinfo.swam.plantnursery.controller;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.GrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.dao.MeasurementDao;
import it.unifi.dinfo.swam.plantnursery.dao.PlantDao;
import it.unifi.dinfo.swam.plantnursery.dao.PositionDao;
import it.unifi.dinfo.swam.plantnursery.dao.SpeciesDao;
import it.unifi.dinfo.swam.plantnursery.dto.PlantDto;
import it.unifi.dinfo.swam.plantnursery.mapper.PlantMapper;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.Measurement;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Species;

@RequestScoped
public class PlantController extends BaseController {

	@Inject
	private PlantDao plantDao;

	@Inject
	private GrowthPlaceDao growthPlaceDao;

	@Inject
	private SpeciesDao speciesDao;

	@Inject
	private PositionDao positionDao;

	@Inject
	private MeasurementDao measurementDao;

	@Inject
	private PlantMapper plantMapper;

	public boolean deletePlant(Long idPlant) {
		this.cleanErrorFields();

		Plant plant = plantDao.findById(idPlant);

		if (plant == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant does not exists");
			return false;
		}

		Position position = positionDao.getPositionByPlant(plant);
		if (position != null) {
			position.setPlant(null);
			positionDao.update(position);
		}

		// Delete plant measures
		List<Measurement> plantMeasures = measurementDao.getFilteredMeasurements(plant, null, null, null, null);
		measurementDao.deleteAll(plantMeasures);

		plantDao.delete(plant);
		return true;
	}

	public List<PlantDto> getFilteredPlants(Long idGrowthPlace, Long idSpecies, Boolean sold, LocalDate dateStart,
			LocalDate dateEnd) {
		this.cleanErrorFields();

		GrowthPlace growthPlace = null;
		if (idGrowthPlace != null) {
			growthPlace = growthPlaceDao.findById(idGrowthPlace);

			if (growthPlace == null) {
				this.setErrorOccurred(true);
				this.setErrorMessage("The growth place does not exists");
				return null;
			}
		}

		Species species = null;
		if (idGrowthPlace != null) {
			species = speciesDao.findById(idSpecies);

			if (species == null) {
				this.setErrorOccurred(true);
				this.setErrorMessage("The species does not exists");
				return null;
			}
		}

		List<PlantDto> plants;
		try {
			plants = plantMapper.toDto(plantDao.getFilteredPlants(growthPlace, species, sold, dateStart, dateEnd));
		} catch (IllegalArgumentException e) {
			this.setErrorOccurred(true);
			this.setErrorMessage(e.getMessage());
			return null;
		}

		return plants;
	}

	public PlantDto getPlantById(Long id) {
		this.cleanErrorFields();

		PlantDto plant;
		try {
			plant = plantMapper.toDto(plantDao.findById(id));
		} catch (IllegalArgumentException e) {
			this.setErrorOccurred(true);
			this.setErrorMessage(e.getMessage());
			return null;
		}

		return plant;
	}

//	public List<Plant> getPlants() {
//		return plantDao.getPlants();
//	}
//	
//	public List<Plant> getPlantsByGrowthPlace(Long idGrowthPlace){
//		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);
//		
//		if(growthPlace == null)
//			return null;
//		
//		return plantDao.getPlantsByGrowthPlace(growthPlace);
//	}
//	
//	public List<Plant> getPlantsByPlantingDate(LocalDate dateStart, LocalDate dateEnd){
//		if(dateEnd.isBefore(dateStart))
//			return null;
//		
//		return plantDao.getPlantsByPlantingDateRange(dateStart, dateEnd);
//	}
//	
//	public List<Plant> getPlantsBySpecies(Long idSpecies){
//		Species species = speciesDao.findById(idSpecies);
//		
//		if(species == null)
//			return null;
//		
//		return plantDao.getPlantsBySpecies(species);
//	}
//	
//	public List<Plant> getNotSoldPlants() {
//		return plantDao.getNotSoldPlants();
//	}
//	
//	public List<Plant> getSoldPlants() {
//		return plantDao.getSoldPlants();
//	}

	public boolean savePlant(PlantDto plantDto) {
		this.cleanErrorFields();

		if (plantDto == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant cannot be null");
			return false;
		}

		Plant plant;
		try {
			plant = plantMapper.toEntity(plantDto);
		} catch (IllegalArgumentException e) {
			this.setErrorOccurred(true);
			this.setErrorMessage(e.getMessage());
			return false;
		}

		plantDao.save(plant);
		return true;
	}

	public boolean updatePlant(PlantDto plantDto) {
		if (plantDto == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant cannot be null");
			return false;
		}

		Plant plant = plantDao.findById(plantDto.getId());
		if (plant == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant to update does not exists");
			return false;
		}
		
		try {
			plant = plantMapper.updateEntity(plant, plantDto);
		} catch (IllegalArgumentException e) {
			this.setErrorOccurred(true);
			this.setErrorMessage(e.getMessage());
			return false;
		}

		plantDao.update(plant);
		return true;
	}

}
