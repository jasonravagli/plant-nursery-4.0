package it.unifi.dinfo.swam.plantnursery.nosql.controller;

import java.util.List;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.GrowthPlaceByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.plant.PlantByFilterDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.plant.PlantByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.plant.PlantByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.plant.PlantBySoldDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.plant.PlantBySpeciesDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByPlantDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionBySensorDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor.SensorByCompanyDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor.SensorByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor.SensorByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor.SensorByMacAddressDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor.SensorByModelDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.PlantDto;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.PositionDto;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.SensorDto;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.PlantMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.PositionMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.SensorMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.model.GrowthPlaceById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.Plant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantByFilter;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantBySold;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantBySpecies;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionByPlant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionBySensor;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.Sensor;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorByCompany;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorByMacAddress;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorByModel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class PositionController extends BaseController {

	@Inject
	private GrowthPlaceByIdDao growthPlaceByIdDao;

	@Inject
	private PositionByIdDao positionByIdDao;

	@Inject
	private PositionByPlantDao positionByPlantDao;

	@Inject
	private PositionByGrowthPlaceDao positionByGrowthPlaceDao;

	@Inject
	private PositionBySensorDao positionBySensorDao;

	@Inject
	private PlantByIdDao plantByIdDao;

	@Inject
	private PlantByGrowthPlaceDao plantByGrowthPlaceDao;

	@Inject
	private PlantByFilterDao plantByFilterDao;

	@Inject
	private PlantBySoldDao plantBySoldDao;

	@Inject
	private PlantBySpeciesDao plantBySpeciesDao;

	@Inject
	private SensorByIdDao sensorByIdDao;

	@Inject
	private SensorByCompanyDao sensorByCompanyDao;

	@Inject
	private SensorByGrowthPlaceDao sensorByGrowthPlaceDao;

	@Inject
	private SensorByMacAddressDao sensorByMacAddressDao;

	@Inject
	private SensorByModelDao sensorByModelDao;

	@Inject
	private PlantMapper plantMapper;

	@Inject
	private PositionMapper positionMapper;

	@Inject
	private SensorMapper sensorMapper;

	public List<PositionDto> getPositionsByGrowthPlace(UUID idGrowthPlace) {
		this.cleanErrorFields();

		GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(idGrowthPlace);

		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return null;
		}

		return positionMapper.toDto(positionByGrowthPlaceDao.getPositionsByGrowthPlace(idGrowthPlace));
	}

	public List<PositionDto> getPlantFreePositionsByGrowthPlace(UUID idGrowthPlace) {
		this.cleanErrorFields();

		GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(idGrowthPlace);

		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return null;
		}

		return positionMapper.toDto(positionByGrowthPlaceDao.getPlantFreePositionsByGrowthPlace(idGrowthPlace));
	}

	public boolean updatePosition(UUID idGrowthPlace, UUID idPosition, PositionDto positionDto) {
		this.cleanErrorFields();

		GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(idGrowthPlace);

		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return false;
		}

		PositionById positionToUpdate = positionByIdDao.findById(idPosition);

		if (positionToUpdate == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The position does not exists");
			return false;
		}

		if ((!positionToUpdate.getGrowthPlaceId().equals(idGrowthPlace))
				|| !positionDto.getIdGrowthPlace().equals(idGrowthPlace)) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Cannot move positions between growth places");
			return false;
		}

		if (positionToUpdate.getRowIndex() != positionDto.getRowIndex()
				|| positionToUpdate.getColumnIndex() != positionDto.getColIndex()) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Cannot change the position indexes");
			return false;
		}

		try {
			positionDto.setFree(positionDto.getIdPlant() != null);
			
			PositionById positionById = positionMapper.toEntity(idPosition, positionDto, PositionById.class);
			PositionByGrowthPlace positionByGrowthPlace = positionMapper.toEntity(idPosition, positionDto,
					PositionByGrowthPlace.class);
			PositionByPlant positionByPlant = positionMapper.toEntity(idPosition, positionDto, PositionByPlant.class);

			positionByIdDao.update(positionById);
			positionByGrowthPlaceDao.update(positionToUpdate, positionByGrowthPlace);
			positionByPlantDao.update(positionToUpdate, positionByPlant);
			
			for(UUID oldIdSensor : positionToUpdate.getListSensors()) {
				positionBySensorDao.delete(oldIdSensor, positionToUpdate.getId());
			}
			for(UUID newIdSensor : positionDto.getListSensors()) {
				PositionBySensor positionBySensor = positionMapper.toEntity(positionToUpdate.getId(), positionDto,
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

		// Update sensors and plants records (add them to the growth place)
		if (!positionToUpdate.getIdPlant().equals(positionDto.getIdPlant())) {
			// If the old position contained a plant than the plant must be removed from the
			// growth place
			if (positionToUpdate.getIdPlant() != null) {
				PlantById oldPlant = plantByIdDao.findById(positionToUpdate.getIdPlant());
				PlantDto plantDto = plantMapper.toDto(oldPlant);
				plantDto.setGrowthPlaceId(null);

				try {
					updatePlantPosition(oldPlant, plantDto);
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
					this.setErrorOccurred(true);
					this.setErrorMessage("An error occured during the conversion from entities to dtos");
					return false;
				}
			}

			// If the new position contains a plant then the plant must be added to the
			// growth place
			if (positionDto.getIdPlant() != null) {
				PlantById oldPlant = plantByIdDao.findById(positionDto.getIdPlant());
				PlantDto plantDto = plantMapper.toDto(oldPlant);
				plantDto.setGrowthPlaceId(positionDto.getIdGrowthPlace());

				try {
					updatePlantPosition(oldPlant, plantDto);
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
					this.setErrorOccurred(true);
					this.setErrorMessage("An error occured during the conversion from entities to dtos");
					return false;
				}
			}
		}

		if (!positionToUpdate.getListSensors().equals(positionDto.getIdPlant())) {
			// If the old position contained some sensors than the sensors must be removed
			// from the growth place
			if (positionToUpdate.getListSensors().size() > 0) {
				for (UUID idSensor : positionToUpdate.getListSensors()) {
					SensorById oldSensor = sensorByIdDao.findById(idSensor);
					SensorDto sensorDto = sensorMapper.toDto(oldSensor);
					sensorDto.setIdGrowthPlace(null);

					try {
						updateSensorPosition(oldSensor, sensorDto);
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
						this.setErrorOccurred(true);
						this.setErrorMessage("An error occured during the conversion from entities to dtos");
						return false;
					}
				}
			}

			// If the new position contains some sensors then the sensors must be added to
			// the growth place
			if (positionDto.getListSensors().size() > 0) {
				for (UUID idSensor : positionDto.getListSensors()) {
					SensorById oldSensor = sensorByIdDao.findById(idSensor);
					SensorDto sensorDto = sensorMapper.toDto(oldSensor);
					sensorDto.setIdGrowthPlace(positionDto.getIdGrowthPlace());

					try {
						updateSensorPosition(oldSensor, sensorDto);
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
						this.setErrorOccurred(true);
						this.setErrorMessage("An error occured during the conversion from entities to dtos");
						return false;
					}
				}
			}
		}

		return true;
	}

	private void updatePlantPosition(Plant oldPlant, PlantDto newPlantDto)
			throws InstantiationException, IllegalAccessException {
		PlantById plantById = plantMapper.toEntity(oldPlant.getId(), newPlantDto, PlantById.class);
		PlantBySpecies plantBySpecies = plantMapper.toEntity(oldPlant.getId(), newPlantDto, PlantBySpecies.class);
		PlantBySold plantBySold = plantMapper.toEntity(oldPlant.getId(), newPlantDto, PlantBySold.class);
		PlantByGrowthPlace plantByGrowthPlace = plantMapper.toEntity(oldPlant.getId(), newPlantDto,
				PlantByGrowthPlace.class);
		PlantByFilter plantByFilter = plantMapper.toEntity(oldPlant.getId(), newPlantDto, PlantByFilter.class);

		plantByIdDao.update(plantById);
		plantBySpeciesDao.update(oldPlant, plantBySpecies);
		plantBySoldDao.update(oldPlant, plantBySold);
		plantByGrowthPlaceDao.update(oldPlant, plantByGrowthPlace);
		plantByFilterDao.update(oldPlant, plantByFilter);
	}

	private void updateSensorPosition(Sensor oldSensor, SensorDto newSensorDto)
			throws InstantiationException, IllegalAccessException {
		SensorById sensorById = sensorMapper.toEntity(oldSensor.getId(), newSensorDto, SensorById.class);
		SensorByCompany sensorByCompany = sensorMapper.toEntity(oldSensor.getId(), newSensorDto, SensorByCompany.class);
		SensorByGrowthPlace sensorByGrowthPlace = sensorMapper.toEntity(oldSensor.getId(), newSensorDto,
				SensorByGrowthPlace.class);
		SensorByMacAddress sensorByMacAddress = sensorMapper.toEntity(oldSensor.getId(), newSensorDto,
				SensorByMacAddress.class);
		SensorByModel sensorByModel = sensorMapper.toEntity(oldSensor.getId(), newSensorDto, SensorByModel.class);

		sensorByIdDao.update(sensorById);
		sensorByCompanyDao.update(oldSensor, sensorByCompany);
		sensorByGrowthPlaceDao.update(oldSensor, sensorByGrowthPlace);
		sensorByMacAddressDao.update(oldSensor, sensorByMacAddress);
		sensorByModelDao.update(oldSensor, sensorByModel);
	}
}
