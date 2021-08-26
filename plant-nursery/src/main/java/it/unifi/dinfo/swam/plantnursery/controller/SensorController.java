package it.unifi.dinfo.swam.plantnursery.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.GrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.dao.MeasurementDao;
import it.unifi.dinfo.swam.plantnursery.dao.PositionDao;
import it.unifi.dinfo.swam.plantnursery.dao.SensorDao;
import it.unifi.dinfo.swam.plantnursery.model.FaultPeriod;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.Measurement;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Sensor;

@RequestScoped
public class SensorController extends BaseController {

	@Inject
	private GrowthPlaceDao growthPlaceDao;

	@Inject
	private MeasurementDao measurementDao;
	
	@Inject
	private PositionDao positionDao;

	@Inject
	private SensorDao sensorDao;

	public boolean deleteSensor(Long idSensor) {
		this.cleanErrorFields();
		
		Sensor sensor = sensorDao.findById(idSensor);

		if (sensor == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor does not exists");
			return false;
		}

		// Remove the sensor from its positions
		List<Position> positions = positionDao.getPositionsBySensor(sensor);
		if(positions.size() > 0) {
			for (Position position : positions) {
				position.removeSensor(sensor);
			}
			positionDao.updateAll(positions);
		}
		
		// Delete sensor measures
		List<Measurement> sensorMeasures = measurementDao.getFilteredMeasurements(null, sensor, null, null, null);
		measurementDao.deleteAll(sensorMeasures);

		sensorDao.delete(sensor);
		return true;
	}

	public Sensor getSensorById(Long id) {
		this.cleanErrorFields();
		
		return sensorDao.findById(id);
	}
	
	public List<Sensor> getFilteredSensors(Long idGrowthPlace, String company, String model, String macAddress, Boolean active) {
		this.cleanErrorFields();
		
		GrowthPlace growthPlace = null;
		if(idGrowthPlace != null) {
			growthPlace = growthPlaceDao.findById(idGrowthPlace);

			if (growthPlace == null) {
				this.setErrorOccurred(true);
				this.setErrorMessage("The growth place does not exists");
				return null;
			}
		}
		
		return sensorDao.getFilteredSensors(growthPlace, company, model, macAddress, active);
	}

//	public List<Sensor> getSensors() {
//		return sensorDao.getSensors();
//	}
//
//	public List<Sensor> getSensorsByGrowthPlace(Long idGrowthPlace) {
//		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);
//
//		if (growthPlace == null)
//			return null;
//
//		return sensorDao.getSensorsByGrowthplace(growthPlace);
//	}
//
//	public List<Sensor> getSensorsByCompany(String company) {
//		return sensorDao.getSensorsByCompany(company);
//	}
//
//	public List<Sensor> getSensorsByModel(String model) {
//		return sensorDao.getSensorsByModel(model);
//	}
//
//	public List<Sensor> getActiveSensors() {
//		return sensorDao.getActiveSensors();
//	}
//
//	public List<Sensor> getDisposedSensors() {
//		return sensorDao.getDisposedSensors();
//	}

	public boolean saveSensor(Sensor sensor) {
		this.cleanErrorFields();

		if (sensor == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor cannot be null");
			return false;
		}
		
		// Check if there is already a sensor with the same MAC address
		if (sensorDao.getSensorByMacAddress(sensor.getMacAddress()) != null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A sensor with the same MAC address already exists");
			return false;
		}

		// Check if fault periods are valid
		for (FaultPeriod fp : sensor.getFaultPeriods()) {
			if (fp.getStartDate() == null || fp.getEndDate().isBefore(fp.getStartDate())) {
				this.setErrorOccurred(true);
				this.setErrorMessage("Fault periods dates are not valid");
				return false;
			}
		}

		sensorDao.save(sensor);
		return true;
	}

	public boolean updateSensor(Sensor sensor) {
		this.cleanErrorFields();
		
		if (sensor == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor cannot be null");
			return false;
		}
		
		if (sensorDao.findById(sensor.getId()) == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor does not exists");
			return false;
		}

		// Check if there is already another sensor with the same MAC address
		Sensor sameAddressSensor = sensorDao.getSensorByMacAddress(sensor.getMacAddress());
		if (sameAddressSensor != null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A sensor with the same MAC address already exists");
			return false;
		}

		// Check if fault periods are valid
		for (FaultPeriod fp : sensor.getFaultPeriods()) {
			if (fp.getStartDate() == null || fp.getEndDate().isBefore(fp.getStartDate())) {
				if (fp.getStartDate() == null || fp.getEndDate().isBefore(fp.getStartDate())) {
					this.setErrorOccurred(true);
					this.setErrorMessage("Fault periods dates are not valid");
					return false;
				}
			}
		}

		sensorDao.update(sensor);
		return true;
	}
}
