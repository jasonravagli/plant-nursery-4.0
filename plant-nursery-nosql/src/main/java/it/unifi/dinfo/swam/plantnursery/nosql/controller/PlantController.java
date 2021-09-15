package it.unifi.dinfo.swam.plantnursery.nosql.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.GrowthPlaceByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.PlantByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.PlantByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.PlantBySoldDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.PlantBySpeciesDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.SpeciesByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.PlantDto;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.PlantMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.model.GrowthPlaceById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantBySold;
import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantBySpecies;
import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesById;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class PlantController extends BaseController {

	@Inject
	private PlantByGrowthPlaceDao plantByGrowthPlaceDao;

	@Inject
	private PlantByIdDao plantByIdDao;

	@Inject
	private PlantBySoldDao plantBySoldDao;

	@Inject
	private PlantBySpeciesDao plantBySpeciesDao;

	@Inject
	private GrowthPlaceByIdDao growthPlaceByIdDao;

	@Inject
	private SpeciesByIdDao speciesByIdDao;

	@Inject
	private PlantMapper plantMapper;

	public boolean deletePlant(UUID idPlant) {
		this.cleanErrorFields();

		PlantById plant = plantByIdDao.findById(idPlant);

		if (plant == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant does not exists");
			return false;
		}

//		Position position = positionDao.getPositionByPlant(plant);
//		if (position != null) {
//			position.setPlant(null);
//			positionDao.update(position);
//		}
//
//		// Delete plant measures
//		List<Measurement> plantMeasures = measurementDao.getFilteredMeasurements(plant, null, null, null, null);
//		measurementDao.deleteAll(plantMeasures);

		plantByIdDao.delete(plant.getId());
		plantBySoldDao.delete(plant.isSold(), plant.getPlantingDate(), plant.getId());
		plantBySpeciesDao.delete(plant.getSpeciesId(), plant.getPlantingDate(), plant.isSold(), plant.getId());

		if (plant.getIdGrowthPlace() != null) {
			plantByGrowthPlaceDao.delete(plant.getIdGrowthPlace(), plant.getPlantingDate(), plant.getId());
		}
		return true;
	}

	public PlantDto getPlantById(UUID id) {
		this.cleanErrorFields();

		PlantById plant = plantByIdDao.findById(id);

		if (plant == null) {
			return null;
		}

		return plantMapper.toDto(plant);
	}

	public boolean savePlant(PlantDto plantDto) throws InstantiationException, IllegalAccessException {
		this.cleanErrorFields();

		if (plantDto == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant cannot be null");
			return false;
		}

		if (plantDto.getSpeciesId() == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant must be assigned to a species");
			return false;
		}

		SpeciesById species = speciesByIdDao.findById(plantDto.getSpeciesId());
		if (species == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species does not exists");
			return false;
		}

		if (!species.getName().equals(plantDto.getSpeciesName())) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species name does not match with the id");
			return false;
		}

		UUID id = Uuids.timeBased();
		try {
			PlantById plantId = plantMapper.toEntity(id, plantDto, PlantById.class);
			PlantBySold plantSold = plantMapper.toEntity(id, plantDto, PlantBySold.class);
			PlantBySpecies plantSpecies = plantMapper.toEntity(id, plantDto, PlantBySpecies.class);

			plantByIdDao.save(plantId);
			plantBySoldDao.save(plantSold);
			plantBySpeciesDao.save(plantSpecies);
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

		PlantById plantToUpdate = plantByIdDao.findById(idPlant);
		if (plantToUpdate == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant to update does not exists");
			return false;
		}

		if (plantDto.getSpeciesId() == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant must be assigned to a species");
			return false;
		}

		SpeciesById species = speciesByIdDao.findById(plantDto.getSpeciesId());
		if (species == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species does not exists");
			return false;
		}

		if (!species.getName().equals(plantDto.getSpeciesName())) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species name does not match with the id");
			return false;
		}

		try {
			PlantById plantById = plantMapper.toEntity(idPlant, plantDto, PlantById.class);
			PlantBySpecies plantBySpecies = plantMapper.toEntity(idPlant, plantDto, PlantBySpecies.class);
			PlantBySold plantBySold = plantMapper.toEntity(idPlant, plantDto, PlantBySold.class);

			plantByIdDao.update(plantById);
			plantBySpeciesDao.update(plantToUpdate, plantBySpecies);
			plantBySoldDao.update(plantToUpdate, plantBySold);

			// If the plant is assigned to a position (growth place) update the record in
			// the PlantByGrowthPlace table
			if (plantToUpdate.getIdGrowthPlace() != null) {
				PlantByGrowthPlace plantByGrowthPlace = plantMapper.toEntity(idPlant, plantDto,
						PlantByGrowthPlace.class);
				plantByGrowthPlaceDao.update(plantToUpdate, plantByGrowthPlace);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			this.setErrorOccurred(true);
			this.setErrorMessage("An error occured during the conversion from entities to dtos");
			return false;
		}

		return true;
	}

	public List<PlantDto> getFilteredPlants(UUID idGrowthPlace, UUID idSpecies, Boolean sold, LocalDate dateStart,
			LocalDate dateEnd) {
		this.cleanErrorFields();

		if (idGrowthPlace != null) {
			GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(idGrowthPlace);

			if (growthPlace == null) {
				this.setErrorOccurred(true);
				this.setErrorMessage("The growth place does not exists");
				return null;
			}
		}

		if (idSpecies != null) {
			SpeciesById species = speciesByIdDao.findById(idSpecies);

			if (species == null) {
				this.setErrorOccurred(true);
				this.setErrorMessage("The species does not exists");
				return null;
			}
		}

		if (idGrowthPlace != null) {
			if (idSpecies != null) {
				if (sold != null) {
					List<PlantByGrowthPlace> plants = plantByGrowthPlaceDao
							.getPlantsByGpAndSpeciesAndSold(idGrowthPlace, idSpecies, sold, dateStart, dateEnd);
					return plantMapper.toDto(plants);
				}

				List<PlantByGrowthPlace> plants = plantByGrowthPlaceDao.getPlantsByGpAndSpecies(idGrowthPlace,
						idSpecies, dateStart, dateEnd);
				return plantMapper.toDto(plants);
			}

			if (sold != null) {
				List<PlantByGrowthPlace> plants = plantByGrowthPlaceDao.getPlantsByGpAndSold(idGrowthPlace, sold,
						dateStart, dateEnd);
				return plantMapper.toDto(plants);
			}

			List<PlantByGrowthPlace> plants = plantByGrowthPlaceDao.getPlantsByGp(idGrowthPlace, dateStart, dateEnd);
			return plantMapper.toDto(plants);
		}

		if (idSpecies != null) {
			if (sold != null) {
				List<PlantBySpecies> plants = plantBySpeciesDao.getPlants(idSpecies, sold, dateStart, dateEnd);
				return plantMapper.toDto(plants);
			}

			List<PlantBySpecies> plants = plantBySpeciesDao.getPlants(idSpecies, dateStart, dateEnd);
			return plantMapper.toDto(plants);
		}

		if (sold != null) {
			List<PlantBySold> plants = plantBySoldDao.getPlants(sold, dateStart, dateEnd);
			return plantMapper.toDto(plants);
		}

		if (dateStart != null || dateEnd != null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Cannot filter by planting date only: an additional filter must be specified");
			return null;
		}

		List<PlantById> plants = plantByIdDao.getPlants();
		return plantMapper.toDto(plants);
	}

}
