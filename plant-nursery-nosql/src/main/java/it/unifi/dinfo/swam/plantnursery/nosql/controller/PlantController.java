package it.unifi.dinfo.swam.plantnursery.nosql.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.GrowthPlaceByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.SpeciesByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament.MeasurementByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament.MeasurementByPlantDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament.MeasurementBySensorDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.plant.PlantByFilterDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.plant.PlantByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.plant.PlantByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.plant.PlantBySoldDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.plant.PlantBySpeciesDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByPlantDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionBySensorDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.PlantDto;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.PositionDto;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.PlantMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.PositionMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.model.GrowthPlaceById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.measurement.MeasurementByPlant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantByFilter;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantBySold;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantBySpecies;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionByPlant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionBySensor;
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
	private PlantByFilterDao plantByFilterDao;

	@Inject
	private GrowthPlaceByIdDao growthPlaceByIdDao;

	@Inject
	private SpeciesByIdDao speciesByIdDao;

	@Inject
	private PositionByPlantDao positionByPlantDao;

	@Inject
	private PositionByGrowthPlaceDao positionByGrowthPlaceDao;

	@Inject
	private PositionByIdDao positionByIdDao;

	@Inject
	private PositionBySensorDao positionBySensorDao;

	@Inject
	private MeasurementByGrowthPlaceDao measurementByGrowthPlaceDao;

	@Inject
	private MeasurementByPlantDao measurementByPlantDao;

	@Inject
	private MeasurementBySensorDao measurementBySensorDao;

	@Inject
	private PlantMapper plantMapper;

	@Inject
	private PositionMapper positionMapper;

	public boolean deletePlant(UUID idPlant) {
		this.cleanErrorFields();

		PlantById plant = plantByIdDao.findById(idPlant);

		if (plant == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant does not exists");
			return false;
		}

		PositionByPlant oldPosition = positionByPlantDao.getPositionByPlant(plant.getId());
		if (oldPosition != null) {
			PositionDto positionDto = positionMapper.toDto(oldPosition);
			positionDto.setFree(true);
			positionDto.setPlantId(null);
			try {
				PositionById positionById = positionMapper.toEntity(oldPosition.getId(), positionDto,
						PositionById.class);
				PositionByGrowthPlace positionByGrowthPlace = positionMapper.toEntity(oldPosition.getId(), positionDto,
						PositionByGrowthPlace.class);

				positionByIdDao.update(positionById);
				positionByGrowthPlaceDao.update(oldPosition, positionByGrowthPlace);
				
				if(oldPosition.getIdPlant() != null) {
					positionByPlantDao.delete(oldPosition.getIdPlant(), oldPosition.getId());
				}
				if(positionDto.getPlantId() != null) {
					PositionByPlant positionByPlant = positionMapper.toEntity(oldPosition.getId(), positionDto, PositionByPlant.class);
					positionByPlantDao.save(positionByPlant);
				}

				for (UUID oldIdSensor : oldPosition.getListSensors()) {
					positionBySensorDao.delete(oldIdSensor, oldPosition.getId());
				}
				for (UUID newIdSensor : positionDto.getListSensorsId()) {
					PositionBySensor positionBySensor = positionMapper.toEntity(oldPosition.getId(), positionDto,
							PositionBySensor.class);
					positionBySensor.setIdSensor(newIdSensor);
					positionBySensorDao.save(positionBySensor);
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				this.setErrorOccurred(true);
				this.setErrorMessage("An error occured during the conversion from entities to dtos");
				return false;
			}
		}

		// Delete plant measures
		List<MeasurementByPlant> plantMeasures = measurementByPlantDao.getMeasurementsByPlant(plant.getId(), null,
				null);
		for (MeasurementByPlant measure : plantMeasures) {
			measurementByGrowthPlaceDao.delete(measure.getIdGrowthPlace(), measure.getMeasDate(), measure.getId());
			measurementByPlantDao.delete(measure.getIdPlant(), measure.getMeasDate(), measure.getId());
			measurementBySensorDao.delete(measure.getIdSensor(), measure.getMeasDate(), measure.getId());
		}

		plantByIdDao.delete(plant.getId());
		plantBySoldDao.delete(plant.isSold(), plant.getPlantingDate(), plant.getId());
		plantBySpeciesDao.delete(plant.getSpeciesId(), plant.getPlantingDate(), plant.getId());

		if (plant.getIdGrowthPlace() != null) {
			plantByGrowthPlaceDao.delete(plant.getIdGrowthPlace(), plant.getPlantingDate(), plant.getId());
			plantByFilterDao.delete(plant.getIdGrowthPlace(), plant.getSpeciesId(), plant.isSold(),
					plant.getPlantingDate(), plant.getId());
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
				PlantByFilter plantByFilter = plantMapper.toEntity(idPlant, plantDto, PlantByFilter.class);

				plantByGrowthPlaceDao.update(plantToUpdate, plantByGrowthPlace);
				plantByFilterDao.update(plantToUpdate, plantByFilter);
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
					List<PlantByFilter> plants = plantByFilterDao.getPlants(idGrowthPlace, idSpecies, sold, dateStart,
							dateEnd);
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
