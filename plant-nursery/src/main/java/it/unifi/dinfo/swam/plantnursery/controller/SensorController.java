package it.unifi.dinfo.swam.plantnursery.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.GrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.dao.PositionDao;
import it.unifi.dinfo.swam.plantnursery.dao.SensorDao;
import it.unifi.dinfo.swam.plantnursery.model.FaultPeriod;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Sensor;

@RequestScoped
public class SensorController {

	@Inject
	private GrowthPlaceDao growthPlaceDao;

	@Inject
	private PositionDao positionDao;

	@Inject
	private SensorDao sensorDao;

	public boolean deleteSensor(Long idSensor) {
		Sensor sensor = sensorDao.findById(idSensor);

		if (sensor == null)
			return false;

		// Remove the sensor from its positions
		List<Position> positions = positionDao.getPositionsBySensor(sensor);
		for (Position position : positions) {
			position.removeSensor(sensor);
		}
		positionDao.updateAll(positions);

		// TODO Delete measures

		sensorDao.delete(sensor);
		return true;
	}

	public Sensor getSensorById(Long id) {
		return sensorDao.findById(id);
	}

	public List<Sensor> getSensors() {
		return sensorDao.getSensors();
	}

	public List<Sensor> getSensorsByGrowthPlace(Long idGrowthPlace) {
		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);

		if (growthPlace == null)
			return null;

		return sensorDao.getSensorsByGrowthplace(growthPlace);
	}

	public List<Sensor> getSensorsByCompany(String company) {
		return sensorDao.getSensorsByCompany(company);
	}

	public List<Sensor> getSensorsByModel(String model) {
		return sensorDao.getSensorsByModel(model);
	}

	public List<Sensor> getActiveSensors() {
		return sensorDao.getActiveSensors();
	}

	public List<Sensor> getDisposedSensors() {
		return sensorDao.getDisposedSensors();
	}

	public boolean saveSensor(Sensor sensor) {
		// Check if there is already a growth place with the same name
		if (sensorDao.getSensorByMacAddress(sensor.getMacAddress()) != null)
			return false;

		// Check if fault periods are valid
		for (FaultPeriod fp : sensor.getFaultPeriods()) {
			if (fp.getStartDate() == null || fp.getEndDate().isBefore(fp.getStartDate()))
				return false;
		}

		sensorDao.save(sensor);
		return true;
	}

	public boolean updateSensor(Sensor sensor) {
		if (sensorDao.findById(sensor.getId()) == null)
			return false;

		// Check if there is already another sensor with the same MAC address
		Sensor sameAddressSensor = sensorDao.getSensorByMacAddress(sensor.getMacAddress());
		if (sameAddressSensor != null)
			return false;

		// Check if fault periods are valid
		for (FaultPeriod fp : sensor.getFaultPeriods()) {
			if (fp.getStartDate() == null || fp.getEndDate().isBefore(fp.getStartDate()))
				return false;
		}

		sensorDao.update(sensor);
		return true;
	}
}
