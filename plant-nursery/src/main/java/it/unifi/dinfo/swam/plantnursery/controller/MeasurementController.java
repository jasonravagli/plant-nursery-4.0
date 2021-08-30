package it.unifi.dinfo.swam.plantnursery.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.GrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.dao.MeasurementDao;
import it.unifi.dinfo.swam.plantnursery.dao.PlantDao;
import it.unifi.dinfo.swam.plantnursery.dao.PositionDao;
import it.unifi.dinfo.swam.plantnursery.dao.SensorDao;
import it.unifi.dinfo.swam.plantnursery.dto.MeasurementDto;
import it.unifi.dinfo.swam.plantnursery.mapper.MeasurementMapper;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.MeasureType;
import it.unifi.dinfo.swam.plantnursery.model.Measurement;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Sensor;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.aggregate.AggregateFunctions;

@RequestScoped
public class MeasurementController extends BaseController {

	@Inject
	private GrowthPlaceDao growthPlaceDao;

	@Inject
	private MeasurementDao measurementDao;

	@Inject
	private PlantDao plantDao;

	@Inject
	private PositionDao positionDao;

	@Inject
	private SensorDao sensorDao;

	@Inject
	private MeasurementMapper measurementMapper;

	public Table getMeasurementsByGrowthPlace(Long idGrowthPlace, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		this.cleanErrorFields();

		// Check if the growth place exists
		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);
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

		List<Measurement> listMeas = measurementDao.getMeasurementsByGrowthPlace(growthPlace, startDateTime,
				endDateTime);
		
		double[] colValues = new double[listMeas.size()];
		LocalDateTime[] colDate = new LocalDateTime[listMeas.size()];
		String[] colType = new String[listMeas.size()];
		for(int i = 0; i < listMeas.size(); i++) {
			colValues[i] = listMeas.get(i).getValue();
			colDate[i] = listMeas.get(i).getDate();
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

	public Table getMeasurementsByPlant(Long idPlant, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.cleanErrorFields();

		// Check if the sensor exists
		Plant plant = plantDao.findById(idPlant);
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

		List<Measurement> listMeas = measurementDao.getMeasurementsByPlant(plant, startDateTime, endDateTime);
		double[] colValues = listMeas.stream().mapToDouble(m -> m.getValue()).toArray();
		LocalDateTime[] colDate = (LocalDateTime[]) listMeas.stream().map(m -> m.getDate()).toArray();
		String[] colType = (String[]) listMeas.stream().map(m -> m.getType().toString()).toArray();

		Table table = Table.create().addColumns(DateTimeColumn.create("timestamp", colDate),
				StringColumn.create("type", colType), DoubleColumn.create("value", colValues));
		table.sortAscendingOn("timestamp");

		return table;
	}

	public Table getMeasurementsBySensor(Long idSensor, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.cleanErrorFields();

		// Check if the sensor exists
		Sensor sensor = sensorDao.findById(idSensor);
		if (sensor == null) {
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

		List<Measurement> listMeas = measurementDao.getMeasurementsBySensor(sensor, startDateTime, endDateTime);
		double[] colValues = listMeas.stream().mapToDouble(m -> m.getValue()).toArray();
		LocalDateTime[] colDate = (LocalDateTime[]) listMeas.stream().map(m -> m.getDate()).toArray();
		String[] colType = (String[]) listMeas.stream().map(m -> m.getType().toString()).toArray();

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
		Sensor sensor = sensorDao.findById(measDto.getIdSensor());
		if (sensor == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor does not exists");
			return false;
		}

		// Check if the sensor measure types contain the type of the measurement
		MeasureType type = MeasureType.valueOf(measDto.getType());
		if (!sensor.getMeasureTypes().contains(type)) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The sensor cannot acquire this type of measurement");
			return false;
		}

		// Retrieve the positions covered by the sensors which gathered the measure
		List<Position> listPositions = positionDao.getPositionsBySensor(sensor);

		// Create a Measurement for each position
		List<Measurement> listMeas = new ArrayList<>();
		for (Position pos : listPositions) {
			Measurement meas = measurementMapper.toEntity(measDto, pos, sensor);
			listMeas.add(meas);
		}
		measurementDao.saveAll(listMeas);

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
