package it.unifi.dinfo.swam.plantnursery.controller;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.GrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.dao.MeasurementDao;
import it.unifi.dinfo.swam.plantnursery.dao.PositionDao;
import it.unifi.dinfo.swam.plantnursery.dao.SensorDao;
import it.unifi.dinfo.swam.plantnursery.model.FaultPeriod;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.Measurement;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
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
		if (positions.size() > 0) {
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

	public List<Sensor> getFilteredSensors(Long idGrowthPlace, String company, String model, String macAddress,
			Boolean active) {
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

		return sensorDao.getFilteredSensors(growthPlace, company, model, macAddress, active);
	}

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

		if (sensor.getMeasureTypes().size() == 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("No measure types specified for this sensor");
			return false;
		}

		// Check if fault periods are valid
		for (FaultPeriod fp : sensor.getFaultPeriods()) {
			if (fp.getStartDate() == null || (fp.getEndDate() != null && fp.getEndDate().isBefore(fp.getStartDate()))) {
				this.setErrorOccurred(true);
				this.setErrorMessage("Fault periods dates are not valid");
				return false;
			}
		}

		Sensor newSensor = ModelFactory.sensor();
		newSensor.setCompany(sensor.getCompany());
		newSensor.setModel(sensor.getModel());
		newSensor.setSerialNumber(sensor.getSerialNumber());
		newSensor.setMacAddress(sensor.getMacAddress());
		newSensor.setMeasureTypes(sensor.getMeasureTypes());
		newSensor.setInstallationDate(sensor.getInstallationDate());
		newSensor.setDisposalDate(sensor.getDisposalDate());
		newSensor.setFaultPeriods(sensor.getFaultPeriods());

		sensorDao.save(newSensor);
		return true;
	}

	public boolean updateSensor(Long idSensor, Sensor sensor) {
		this.cleanErrorFields();

		if (sensor == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor cannot be null");
			return false;
		}

		Sensor sensorToUpdate = sensorDao.findById(idSensor);
		if (sensorToUpdate == null) {
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

		if (sensor.getMeasureTypes().size() == 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("No measure types specified for this sensor");
			return false;
		}

		// Check if fault periods are valid
		for (FaultPeriod fp : sensor.getFaultPeriods()) {
			if (fp.getStartDate() == null || (fp.getEndDate() != null && fp.getEndDate().isBefore(fp.getStartDate()))) {
				this.setErrorOccurred(true);
				this.setErrorMessage("Fault periods dates are not valid");
				return false;
			}
		}

		sensorToUpdate.setCompany(sensor.getCompany());
		sensorToUpdate.setModel(sensor.getModel());
		sensorToUpdate.setSerialNumber(sensor.getSerialNumber());
		sensorToUpdate.setMacAddress(sensor.getMacAddress());
		sensorToUpdate.setMeasureTypes(sensor.getMeasureTypes());
		sensorToUpdate.setInstallationDate(sensor.getInstallationDate());
		sensorToUpdate.setDisposalDate(sensor.getDisposalDate());
		sensorToUpdate.setFaultPeriods(sensor.getFaultPeriods());

		sensorDao.update(sensorToUpdate);
		return true;
	}
}
