package it.unifi.dinfo.swam.plantnursery.nosql.controller;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.GrowthPlaceByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament.MeasurementByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament.MeasurementByPlantDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament.MeasurementBySensorDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByPlantDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionBySensorDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor.SensorByCompanyDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor.SensorByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor.SensorByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor.SensorByMacAddressDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor.SensorByModelDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.FaultPeriodDto;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.PositionDto;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.SensorDto;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.PositionMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.SensorMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.model.GrowthPlaceById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.measurement.MeasurementBySensor;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionByPlant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionBySensor;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorByCompany;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorByMacAddress;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorByModel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class SensorController extends BaseController {

	@Inject
	private SensorByIdDao sensorByIdDao;

	@Inject
	private SensorByMacAddressDao sensorByMacAddressDao;

	@Inject
	private SensorByCompanyDao sensorByCompanyDao;

	@Inject
	private SensorByModelDao sensorByModelDao;

	@Inject
	private SensorByGrowthPlaceDao sensorByGrowthPlaceDao;

	@Inject
	private GrowthPlaceByIdDao growthPlaceByIdDao;

	@Inject
	private PositionByIdDao positionByIdDao;

	@Inject
	private PositionByGrowthPlaceDao positionByGrowthPlaceDao;

	@Inject
	private PositionByPlantDao positionByPlantDao;

	@Inject
	private PositionBySensorDao positionBySensorDao;

	@Inject
	private MeasurementByGrowthPlaceDao measurementByGrowthPlaceDao;

	@Inject
	private MeasurementByPlantDao measurementByPlantDao;

	@Inject
	private MeasurementBySensorDao measurementBySensorDao;

	@Inject
	private PositionMapper positionMapper;

	@Inject
	private SensorMapper sensorMapper;

	public boolean deleteSensor(UUID idSensor) {
		this.cleanErrorFields();

		SensorById sensor = sensorByIdDao.findById(idSensor);

		if (sensor == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor does not exists");
			return false;
		}

		// Remove the sensor from its positions
		List<PositionBySensor> positions = positionBySensorDao.getPositionsBySensor(sensor.getId());
		try {
			if (positions.size() > 0) {
				for (PositionBySensor oldPosition : positions) {
					PositionDto positionDto = positionMapper.toDto(oldPosition);
					Set<UUID> listSensors = positionDto.getListSensorsId();
					listSensors.remove(sensor.getId());
					positionDto.setListSensorsId(listSensors);

					PositionById positionById = positionMapper.toEntity(oldPosition.getId(), positionDto,
							PositionById.class);
					PositionByGrowthPlace positionByGrowthPlace = positionMapper.toEntity(oldPosition.getId(),
							positionDto, PositionByGrowthPlace.class);
					PositionByPlant positionByPlant = positionMapper.toEntity(oldPosition.getId(), positionDto,
							PositionByPlant.class);

					positionByIdDao.update(positionById);
					positionByGrowthPlaceDao.update(oldPosition, positionByGrowthPlace);
					positionBySensorDao.delete(sensor.getId(), oldPosition.getId());
					
					if(oldPosition.getIdPlant() != null) {						
						positionByPlantDao.update(oldPosition, positionByPlant);
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			this.setErrorOccurred(true);
			this.setErrorMessage("An error occured during the conversion from entities to dtos");
			return false;
		}

		// Delete sensor measures
		List<MeasurementBySensor> sensorMeasures = measurementBySensorDao.getMeasurementsBySensor(sensor.getId(), null,
				null);
		for(MeasurementBySensor measure : sensorMeasures) {
			measurementByGrowthPlaceDao.delete(measure.getIdGrowthPlace(), measure.getMeasDate(), measure.getId());
			measurementByPlantDao.delete(measure.getIdPlant(), measure.getMeasDate(), measure.getId());
			measurementBySensorDao.delete(measure.getIdSensor(), measure.getMeasDate(), measure.getId());
		}

		sensorByIdDao.delete(sensor.getId());
		sensorByMacAddressDao.delete(sensor.getMacAddress());
		sensorByCompanyDao.delete(sensor.getCompany(), sensor.getModel(), sensor.getId());
		sensorByModelDao.delete(sensor.getModel(), sensor.getId());

		if (sensor.getIdGrowthPlace() != null) {
			sensorByGrowthPlaceDao.delete(sensor.getIdGrowthPlace(), sensor.getCompany(), sensor.getModel(),
					sensor.getId());
		}

		return true;
	}

	public SensorDto getSensorById(UUID id) {
		this.cleanErrorFields();

		SensorById sensor = sensorByIdDao.findById(id);

		if (sensor == null)
			return null;

		return sensorMapper.toDto(sensor);
	}

	public List<SensorDto> getFilteredSensors(UUID idGrowthPlace, String company, String model, String macAddress) {
		this.cleanErrorFields();

		if (idGrowthPlace != null) {
			GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(idGrowthPlace);

			if (growthPlace == null) {
				this.setErrorOccurred(true);
				this.setErrorMessage("The growth place does not exists");
				return null;
			}
		}

		if (macAddress != null) {
			SensorByMacAddress sensorByMacAddress = sensorByMacAddressDao.getSensorByMacAddress(macAddress);
			return Collections.singletonList(sensorMapper.toDto(sensorByMacAddress));
		}

		// Cover all the possible combinations of parameters to retrieve the filtered
		// sensors
		if (idGrowthPlace != null) {
			if (company != null) {
				if (model != null) {
					List<SensorByGrowthPlace> sensors = sensorByGrowthPlaceDao
							.getSensorsByGpAndCompanyAndModel(idGrowthPlace, company, model);
					return sensorMapper.toDto(sensors);
				}

				List<SensorByGrowthPlace> sensors = sensorByGrowthPlaceDao.getSensorsByGpAndCompany(idGrowthPlace,
						company);
				return sensorMapper.toDto(sensors);
			}
			if (model != null) {
				List<SensorByGrowthPlace> sensors = sensorByGrowthPlaceDao.getSensorsByGpAndModel(idGrowthPlace, model);
				return sensorMapper.toDto(sensors);
			}

			List<SensorByGrowthPlace> sensors = sensorByGrowthPlaceDao.getSensorsByGp(idGrowthPlace);
			return sensorMapper.toDto(sensors);
		}

		if (company != null) {
			if (model != null) {
				List<SensorByCompany> sensors = sensorByCompanyDao.getSensors(company, model);
				return sensorMapper.toDto(sensors);
			}

			List<SensorByCompany> sensors = sensorByCompanyDao.getSensors(company);
			return sensorMapper.toDto(sensors);
		}

		if (model != null) {
			List<SensorByModel> sensors = sensorByModelDao.getSensors(model);
			return sensorMapper.toDto(sensors);
		}

		List<SensorById> sensors = sensorByIdDao.getSensors();
		return sensorMapper.toDto(sensors);
	}

	public boolean saveSensor(SensorDto sensorDto) {
		this.cleanErrorFields();

		if (sensorDto == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor cannot be null");
			return false;
		}

		// Check if there is already a sensor with the same MAC address
		if (sensorByMacAddressDao.getSensorByMacAddress(sensorDto.getMacAddress()) != null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A sensor with the same MAC address already exists");
			return false;
		}

		if (sensorDto.getMeasureTypes().size() == 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("No measure types specified for this sensor");
			return false;
		}

		// Check if fault periods are valid
		for (FaultPeriodDto fp : sensorDto.getFaultPeriods()) {
			if (fp.getStartDate() == null || (fp.getEndDate() != null && fp.getEndDate().isBefore(fp.getStartDate()))) {
				this.setErrorOccurred(true);
				this.setErrorMessage("Fault periods dates are not valid");
				return false;
			}
		}

		UUID id = Uuids.timeBased();
		try {
			SensorById sensorById = sensorMapper.toEntity(id, sensorDto, SensorById.class);
			SensorByMacAddress sensorByMacAddress = sensorMapper.toEntity(id, sensorDto, SensorByMacAddress.class);
			SensorByCompany sensorByCompany = sensorMapper.toEntity(id, sensorDto, SensorByCompany.class);
			SensorByModel sensorByModel = sensorMapper.toEntity(id, sensorDto, SensorByModel.class);

			sensorByIdDao.save(sensorById);
			sensorByMacAddressDao.save(sensorByMacAddress);
			sensorByCompanyDao.save(sensorByCompany);
			sensorByModelDao.save(sensorByModel);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			this.setErrorOccurred(true);
			this.setErrorMessage("An error occured during the  conversion from entities to dtos");
			return false;
		}

		return true;
	}

	public boolean updateSensor(UUID idSensor, SensorDto sensorDto) {
		this.cleanErrorFields();

		if (sensorDto == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor cannot be null");
			return false;
		}

		SensorById sensorToUpdate = sensorByIdDao.findById(idSensor);
		if (sensorToUpdate == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor does not exists");
			return false;
		}

		// Check if there is already another sensor with the same MAC address
		SensorByMacAddress sameAddressSensor = sensorByMacAddressDao.getSensorByMacAddress(sensorDto.getMacAddress());
		if (sameAddressSensor != null && !sensorToUpdate.equals(sameAddressSensor)) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A sensor with the same MAC address already exists");
			return false;
		}

		if (sensorDto.getMeasureTypes().size() == 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("No measure types specified for this sensor");
			return false;
		}

		// Check if fault periods are valid
		for (FaultPeriodDto fp : sensorDto.getFaultPeriods()) {
			if (fp.getStartDate() == null || (fp.getEndDate() != null && fp.getEndDate().isBefore(fp.getStartDate()))) {
				this.setErrorOccurred(true);
				this.setErrorMessage("Fault periods dates are not valid");
				return false;
			}
		}

		try {
			SensorById sensorById = sensorMapper.toEntity(idSensor, sensorDto, SensorById.class);
			SensorByMacAddress sensorByMacAddress = sensorMapper.toEntity(idSensor, sensorDto,
					SensorByMacAddress.class);
			SensorByCompany sensorByCompany = sensorMapper.toEntity(idSensor, sensorDto, SensorByCompany.class);
			SensorByModel sensorByModel = sensorMapper.toEntity(idSensor, sensorDto, SensorByModel.class);

			sensorByIdDao.update(sensorById);
			sensorByMacAddressDao.update(sensorToUpdate, sensorByMacAddress);
			sensorByCompanyDao.update(sensorToUpdate, sensorByCompany);
			sensorByModelDao.update(sensorToUpdate, sensorByModel);

			// If the sensor is assigned to a position (growth place) update the record in
			// the SensorByGrowthPlace table
			if (sensorToUpdate.getIdGrowthPlace() != null) {
				SensorByGrowthPlace sensorByGrowthPlace = sensorMapper.toEntity(idSensor, sensorDto,
						SensorByGrowthPlace.class);
				sensorByGrowthPlaceDao.update(sensorToUpdate, sensorByGrowthPlace);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			this.setErrorOccurred(true);
			this.setErrorMessage("An error occured during the conversion from entities to dtos");
			return false;
		}

		return true;
	}
}
