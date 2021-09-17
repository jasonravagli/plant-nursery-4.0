package it.unifi.dinfo.swam.plantnursery.nosql.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.GrowthPlaceByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament.MeasurementByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament.MeasurementByPlantDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.measurament.MeasurementBySensorDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.plant.PlantByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionBySensorDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor.SensorByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.MeasurementDto;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.MeasureType;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.MeasurementMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.model.GrowthPlaceById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.measurement.MeasurementByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.measurement.MeasurementByPlant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.measurement.MeasurementBySensor;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionBySensor;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorById;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import tech.tablesaw.aggregate.AggregateFunctions;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

@RequestScoped
public class MeasurementController extends BaseController {

	@Inject
	private GrowthPlaceByIdDao growthPlaceByIdDao;

	@Inject
	private MeasurementByGrowthPlaceDao measurementByGrowthPlaceDao;

	@Inject
	private MeasurementByPlantDao measurementByPlantDao;

	@Inject
	private MeasurementBySensorDao measurementBySensorDao;

	@Inject
	private PlantByIdDao plantByIdDao;

	@Inject
	private PositionByIdDao positionByIdDao;

	@Inject
	private PositionBySensorDao positionBySensorDao;

	@Inject
	private SensorByIdDao sensorByIdDao;

	@Inject
	private MeasurementMapper measurementMapper;

	public Table getMeasurementsByGrowthPlace(UUID idGrowthPlace, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		this.cleanErrorFields();

		// Check if the growth place exists
		GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(idGrowthPlace);
		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return null;
		}

		if (startDateTime == null || endDateTime == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A time interval must be specified");
			return null;
		}

		if (endDateTime.isBefore(startDateTime)) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The end date must be after the start date");
			return null;
		}

		List<MeasurementByGrowthPlace> listMeas = measurementByGrowthPlaceDao
				.getMeasurementsByGrowthPlace(growthPlace.getId(), startDateTime, endDateTime);

		double[] colValues = new double[listMeas.size()];
		LocalDateTime[] colDate = new LocalDateTime[listMeas.size()];
		String[] colType = new String[listMeas.size()];
		for (int i = 0; i < listMeas.size(); i++) {
			colValues[i] = listMeas.get(i).getValue();
			colDate[i] = listMeas.get(i).getMeasDate();
			colType[i] = listMeas.get(i).getType().toString();
		}

		Table table = Table.create().addColumns(DateTimeColumn.create("timestamp", colDate),
				StringColumn.create("type", colType), DoubleColumn.create("value", colValues));
		table.sortAscendingOn("timestamp");

		Table aggregatedMeasures = table
				.summarize("value", AggregateFunctions.mean, AggregateFunctions.stdDev, AggregateFunctions.min,
						AggregateFunctions.max)
				.by(table.dateTimeColumn("timestamp").timeWindow(ChronoUnit.MINUTES, 10), table.stringColumn("type"));

		return aggregatedMeasures;
	}

	public Table getMeasurementsByPlant(UUID idPlant, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.cleanErrorFields();

		// Check if the plant exists
		PlantById plant = plantByIdDao.findById(idPlant);
		if (plant == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The plant does not exists");
			return null;
		}

		if (startDateTime == null || endDateTime == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A time interval must be specified");
			return null;
		}

		if (endDateTime.isBefore(startDateTime)) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The end date must be after the start date");
			return null;
		}

		List<MeasurementByPlant> listMeas = measurementByPlantDao.getMeasurementsByPlant(plant.getId(), startDateTime,
				endDateTime);

		double[] colValues = new double[listMeas.size()];
		LocalDateTime[] colDate = new LocalDateTime[listMeas.size()];
		String[] colType = new String[listMeas.size()];
		for (int i = 0; i < listMeas.size(); i++) {
			colValues[i] = listMeas.get(i).getValue();
			colDate[i] = listMeas.get(i).getMeasDate();
			colType[i] = listMeas.get(i).getType().toString();
		}

		Table table = Table.create().addColumns(DateTimeColumn.create("timestamp", colDate),
				StringColumn.create("type", colType), DoubleColumn.create("value", colValues));
		table.sortAscendingOn("timestamp");

		return table;
	}

	public Table getMeasurementsBySensor(UUID idSensor, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.cleanErrorFields();

		// Check if the sensor exists
		SensorById sensor = sensorByIdDao.findById(idSensor);
		if (sensor == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor does not exists");
			return null;
		}

		if (startDateTime == null || endDateTime == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A time interval must be specified");
			return null;
		}

		if (endDateTime.isBefore(startDateTime)) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The end date must be after the start date");
			return null;
		}

		List<MeasurementBySensor> listMeas = measurementBySensorDao.getMeasurementsBySensor(sensor.getId(),
				startDateTime, endDateTime);

		double[] colValues = new double[listMeas.size()];
		LocalDateTime[] colDate = new LocalDateTime[listMeas.size()];
		String[] colType = new String[listMeas.size()];
		for (int i = 0; i < listMeas.size(); i++) {
			colValues[i] = listMeas.get(i).getValue();
			colDate[i] = listMeas.get(i).getMeasDate();
			colType[i] = listMeas.get(i).getType().toString();
		}

		Table table = Table.create().addColumns(DateTimeColumn.create("timestamp", colDate),
				StringColumn.create("type", colType), DoubleColumn.create("value", colValues));
		table.sortAscendingOn("timestamp");

		return table;
	}

	public boolean saveMeasurement(MeasurementDto measDto) {
		this.cleanErrorFields();

		if (!areAllRequiredFieldsFilled(measDto)) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Invalid measurement: missing mandatory fields");
			return false;
		}

		// Check if the sensor exists
		SensorById sensor = sensorByIdDao.findById(measDto.getIdSensor());
		if (sensor == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor does not exists");
			return false;
		}

		// Check if the sensor measure types contain the type of the measurement
		MeasureType type = MeasureType.valueOf(measDto.getType());
		if (!sensor.getMeasureTypes().contains(type.toString())) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor cannot acquire this type of measurement");
			return false;
		}

		// Retrieve the positions covered by the sensors which gathered the measure
		List<PositionBySensor> listPositions = positionBySensorDao.getPositionsBySensor(sensor.getId());

		// Create a Measurement for each position
		try {
			for (PositionBySensor pos : listPositions) {
				UUID id = Uuids.timeBased();

				MeasurementByGrowthPlace measGP = measurementMapper.toEntity(id, measDto, pos.getGrowthPlaceId(),
						pos.getId(), pos.getIdPlant(), sensor.getId(), MeasurementByGrowthPlace.class);
				MeasurementByPlant measPlant = measurementMapper.toEntity(id, measDto, pos.getGrowthPlaceId(),
						pos.getId(), pos.getIdPlant(), sensor.getId(), MeasurementByPlant.class);
				MeasurementBySensor measSensor = measurementMapper.toEntity(id, measDto, pos.getGrowthPlaceId(),
						pos.getId(), pos.getIdPlant(), sensor.getId(), MeasurementBySensor.class);

				measurementByGrowthPlaceDao.save(measGP);
				measurementByPlantDao.save(measPlant);
				measurementBySensorDao.save(measSensor);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			this.setErrorOccurred(true);
			this.setErrorMessage("An error occured during the conversion from entities to dtos");
			return false;
		}

		return true;
	}

	private boolean areAllRequiredFieldsFilled(MeasurementDto meas) {
		if (meas.getDate() == null)
			return false;

		if (meas.getType() == null)
			return false;

		if (meas.getUnit() == null)
			return false;

		if (meas.getIdSensor() == null)
			return false;

		return true;
	}
}
