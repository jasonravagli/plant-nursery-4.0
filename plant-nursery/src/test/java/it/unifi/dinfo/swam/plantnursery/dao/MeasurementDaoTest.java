package it.unifi.dinfo.swam.plantnursery.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.MeasureType;
import it.unifi.dinfo.swam.plantnursery.model.Measurement;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Sensor;
import it.unifi.dinfo.swam.plantnursery.model.Unit;

public class MeasurementDaoTest extends JpaTest {

	private MeasurementDao measurementDao;
	
	private LocalDateTime date1 = LocalDateTime.parse("2021-08-19T18:17:00");
	private float value1 = 5;
	private MeasureType type1 = MeasureType.SOIL_PH;
	private Unit unit1 = Unit.PH_POINTS;
	private Measurement measurement1;
	
	private LocalDateTime date2 = LocalDateTime.parse("2021-08-19T18:17:30");;
	private float value2 = 25;
	private MeasureType type2 = MeasureType.SOIL_MOISTURE;
	private Unit unit2 = Unit.PERCENTAGE;
	private Measurement measurement2;
	
	private LocalDateTime date3 = LocalDateTime.parse("2021-08-18T08:33:14");;
	private float value3 = 18;
	private MeasureType type3 = MeasureType.TEMPERATURE;
	private Unit unit3 = Unit.CELSIUS;
	private Measurement measurement3;
	
	// Sensor for measurements 1 and 2
	private Sensor sensor12;
	// Sensor for measurement 3
	private Sensor sensor3;
	
	private Plant plant1;
	private Plant plant2;
	private Plant plant3;
	
	// Growth place for measurements 1 and 2
	private GrowthPlace growthPlace12;
	// Growth place for measurement 3
	private GrowthPlace growthPlace3;
	
	private Position position1;
	private Position position2;
	private Position position3;
	
	@Override
	protected void init() throws IllegalAccessException {
		measurementDao = new MeasurementDao(this.entityManager);
		
		measurement1 = ModelFactory.measurement();
		measurement1.setDate(date1);
		measurement1.setType(type1);
		measurement1.setUnit(unit1);
		measurement1.setValue(value1);
		
		measurement2 = ModelFactory.measurement();
		measurement2.setDate(date2);
		measurement2.setType(type2);
		measurement2.setUnit(unit2);
		measurement2.setValue(value2);
		
		measurement3 = ModelFactory.measurement();
		measurement3.setDate(date3);
		measurement3.setType(type3);
		measurement3.setUnit(unit3);
		measurement3.setValue(value3);
		
		sensor12 = ModelFactory.sensor();
		sensor3 = ModelFactory.sensor();
		measurement1.setSensor(sensor12);
		measurement2.setSensor(sensor12);
		measurement3.setSensor(sensor3);
		
		plant1 = FakeModelFactory.plant();
		plant2 = FakeModelFactory.plant();
		plant3 = FakeModelFactory.plant();
		measurement1.setPlant(plant1);
		measurement2.setPlant(plant2);
		measurement3.setPlant(plant3);
		
		growthPlace12 = FakeModelFactory.growthPlace();
		growthPlace3 = FakeModelFactory.growthPlace();
		
		position1 = ModelFactory.position();
		position1.setGrowthPlace(growthPlace12);
		position2 = ModelFactory.position();
		position2.setGrowthPlace(growthPlace12);
		position3 = ModelFactory.position();
		position3.setGrowthPlace(growthPlace3);
		measurement1.setPosition(position1);
		measurement2.setPosition(position2);
		measurement3.setPosition(position3);
		
		this.entityManager.persist(growthPlace12);
		this.entityManager.persist(growthPlace3);
		this.entityManager.persist(position1);
		this.entityManager.persist(position2);
		this.entityManager.persist(position3);
		this.entityManager.persist(sensor12);
		this.entityManager.persist(sensor3);
		this.entityManager.persist(plant1.getSpecies());
		this.entityManager.persist(plant2.getSpecies());
		this.entityManager.persist(plant3.getSpecies());
		this.entityManager.persist(plant1);
		this.entityManager.persist(plant2);
		this.entityManager.persist(plant3);
		this.entityManager.persist(measurement1);
		this.entityManager.persist(measurement2);
		this.entityManager.persist(measurement3);
	}
	
	// ---- Testing BaseDAO methods

	@Test
	public void testDelete() {
		measurementDao.delete(measurement1);

		Measurement retrievedMeas = this.entityManager.find(Measurement.class, measurement1.getId());
		assertNull(retrievedMeas);
	}
	
	@Test
	public void testDeleteAll() {
		measurementDao.deleteAll(Stream.of(measurement1, measurement2).collect(Collectors.toList()));

		Measurement retrievedMeas = this.entityManager.find(Measurement.class, measurement1.getId());
		assertNull(retrievedMeas);
		retrievedMeas = this.entityManager.find(Measurement.class, measurement2.getId());
		assertNull(retrievedMeas);
	}

	@Test
	public void testFindById() {
		Measurement retrievedMeas = measurementDao.findById(measurement1.getId());

		assertTrue(this.areMeasurementsEqual(retrievedMeas, measurement1));
	}

	@Test
	public void testFindByIdWithWrongId() {
		Measurement retrievedMeas = measurementDao.findById(0L);

		assertNull(retrievedMeas);
	}

	@Test
	public void testSave() {
		Measurement meas = ModelFactory.measurement();
		meas.setDate(LocalDateTime.parse("2021-08-19T19:14:00"));
		meas.setPlant(FakeModelFactory.plant());
		meas.setSensor(ModelFactory.sensor());
		meas.setValue(30);
		meas.setType(MeasureType.LIGHT);
		meas.setUnit(Unit.LUX);
		Position position = ModelFactory.position();
		position.setGrowthPlace(growthPlace3);
		meas.setPosition(position);
		
		this.entityManager.persist(meas.getPlant().getSpecies());
		this.entityManager.persist(meas.getPlant());
		this.entityManager.persist(meas.getPosition());
		this.entityManager.persist(meas.getSensor());
		measurementDao.save(meas);

		Measurement retrievedMeas = this.entityManager.find(Measurement.class, meas.getId());

		assertEquals(true, this.areMeasurementsEqual(retrievedMeas, meas));
	}
	
	@Test
	public void testSaveAll() {
		Measurement newMeas1 = ModelFactory.measurement();
		newMeas1.setDate(LocalDateTime.parse("2021-08-19T19:16:00"));
		newMeas1.setPlant(FakeModelFactory.plant());
		newMeas1.setSensor(ModelFactory.sensor());
		newMeas1.setValue(30);
		newMeas1.setType(MeasureType.LIGHT);
		newMeas1.setUnit(Unit.LUX);
		Position newPos1 = ModelFactory.position();
		newPos1.setGrowthPlace(growthPlace12);
		newMeas1.setPosition(newPos1);
		
		Measurement newMeas2 = ModelFactory.measurement();
		newMeas2.setDate(LocalDateTime.parse("2021-08-19T19:16:30"));
		newMeas2.setPlant(FakeModelFactory.plant());
		newMeas2.setSensor(ModelFactory.sensor());
		newMeas2.setValue(6);
		newMeas2.setType(MeasureType.SOIL_PH);
		newMeas2.setUnit(Unit.PH_POINTS);
		Position newPos2 = ModelFactory.position();
		newPos2.setGrowthPlace(growthPlace3);
		newMeas2.setPosition(newPos2);
		
		this.entityManager.persist(newMeas1.getPlant().getSpecies());
		this.entityManager.persist(newMeas1.getPlant());
		this.entityManager.persist(newMeas1.getPosition());
		this.entityManager.persist(newMeas1.getSensor());
		this.entityManager.persist(newMeas2.getPlant().getSpecies());
		this.entityManager.persist(newMeas2.getPlant());
		this.entityManager.persist(newMeas2.getPosition());
		this.entityManager.persist(newMeas2.getSensor());
		measurementDao.saveAll(Stream.of(newMeas1, newMeas2).collect(Collectors.toList()));

		Measurement retrievedMeas = this.entityManager.find(Measurement.class, newMeas1.getId());
		assertEquals(true, this.areMeasurementsEqual(retrievedMeas, newMeas1));
		retrievedMeas = this.entityManager.find(Measurement.class, newMeas2.getId());
		assertEquals(true, this.areMeasurementsEqual(retrievedMeas, newMeas2));
	}

	@Test
	public void testUpdate() {
		measurement1.setDate(LocalDateTime.parse("2021-08-19T19:14:00"));
		measurement1.setPlant(FakeModelFactory.plant());
		measurement1.setSensor(ModelFactory.sensor());
		measurement1.setValue(30);
		measurement1.setType(MeasureType.LIGHT);
		measurement1.setUnit(Unit.LUX);
		Position position = ModelFactory.position();
		position.setGrowthPlace(growthPlace3);
		measurement1.setPosition(position);
		
		this.entityManager.persist(measurement1.getPlant().getSpecies());
		this.entityManager.persist(measurement1.getPlant());
		this.entityManager.persist(measurement1.getSensor());
		this.entityManager.persist(measurement1.getPosition());
		measurementDao.update(measurement1);

		Measurement retrievedMeas = this.entityManager.find(Measurement.class, measurement1.getId());

		assertEquals(true, this.areMeasurementsEqual(retrievedMeas, measurement1));
	}

	// ----- Testing PositionDao methods
	
	@Test
	public void testGetMeasurementsBySensor() {
		LocalDateTime startDateTime = date1;
		LocalDateTime endDateTime = date2;
		List<Measurement> retrievedMeas = measurementDao.getMeasurementsBySensor(sensor12, startDateTime, endDateTime);

		assertEquals(2, retrievedMeas.size());
		assertEquals(true, retrievedMeas.stream().anyMatch(m -> areMeasurementsEqual(m, measurement1)));
		assertEquals(true, retrievedMeas.stream().anyMatch(m -> areMeasurementsEqual(m, measurement2)));
	}
	
	@Test
	public void testGetMeasurementsBySensorWhenNoMeasuresInTimeInterval() {
		LocalDateTime startDateTime = date1.minusDays(1);
		LocalDateTime endDateTime = date2.minusDays(1);
		List<Measurement> retrievedMeas = measurementDao.getMeasurementsBySensor(sensor12, startDateTime, endDateTime);

		assertEquals(0, retrievedMeas.size());
	}
	
	@Test
	public void testGetMeasurementsBySensorWhenTimeIntervalIsInvalid() {
		LocalDateTime startDateTime = date1.plusHours(1);
		LocalDateTime endDateTime = date1;
		assertThrows(IllegalArgumentException.class, () -> measurementDao.getMeasurementsBySensor(sensor12, startDateTime, endDateTime));
	}
	
	@Test
	public void testGetMeasurementsByPlant() {
		LocalDateTime startDateTime = date3.minusHours(1);
		LocalDateTime endDateTime = date3;
		List<Measurement> retrievedMeas = measurementDao.getMeasurementsByPlant(plant3, startDateTime, endDateTime);

		assertEquals(1, retrievedMeas.size());
		assertEquals(true, retrievedMeas.stream().anyMatch(m -> areMeasurementsEqual(m, measurement3)));
	}
	
	@Test
	public void testGetMeasurementsByPlantWhenNoMeasuresInTimeInterval() {
		LocalDateTime startDateTime = date3.minusHours(1).minusDays(1);
		LocalDateTime endDateTime = date3.minusDays(1);
		List<Measurement> retrievedMeas = measurementDao.getMeasurementsByPlant(plant3, startDateTime, endDateTime);

		assertEquals(0, retrievedMeas.size());
	}
	
	@Test
	public void testGetMeasurementsByPlantWhenTimeIntervalIsInvalid() {
		LocalDateTime startDateTime = date3.plusHours(1);
		LocalDateTime endDateTime = date3;
		assertThrows(IllegalArgumentException.class, () -> measurementDao.getMeasurementsByPlant(plant3, startDateTime, endDateTime));
	}
	
	@Test
	public void testGetMeasurementsByGrowthPlace() {
		LocalDateTime startDateTime = date1;
		LocalDateTime endDateTime = date2;
		List<Measurement> retrievedMeas = measurementDao.getMeasurementsByGrowthPlace(growthPlace12, startDateTime, endDateTime);

		assertEquals(2, retrievedMeas.size());
		assertEquals(true, retrievedMeas.stream().anyMatch(m -> areMeasurementsEqual(m, measurement1)));
		assertEquals(true, retrievedMeas.stream().anyMatch(m -> areMeasurementsEqual(m, measurement2)));
	}
	
	@Test
	public void testGetMeasurementsByGrowthPlaceWhenNoMeasuresInTimeInterval() {
		LocalDateTime startDateTime = date1.minusDays(1);
		LocalDateTime endDateTime = date2.minusDays(1);
		List<Measurement> retrievedMeas = measurementDao.getMeasurementsByGrowthPlace(growthPlace12, startDateTime, endDateTime);

		assertEquals(0, retrievedMeas.size());
	}
	
	@Test
	public void testGetMeasurementsByGrowthPlaceWhenTimeIntervalIsInvalid() {
		LocalDateTime startDateTime = date2;
		LocalDateTime endDateTime = date1;
		assertThrows(IllegalArgumentException.class, () -> measurementDao.getMeasurementsByGrowthPlace(growthPlace12, startDateTime, endDateTime));
	}
	
	// Method for field-based equality check between Position entities
	private boolean areMeasurementsEqual(Measurement meas1, Measurement meas2) {
		if (meas1 == null || meas2 == null)
			return false;
		if (!meas1.getId().equals(meas2.getId()))
			return false;
		if (!meas1.getUuid().equals(meas2.getUuid()))
			return false;
		if (!meas1.getDate().equals(meas2.getDate()))
			return false;
		if (!meas1.getType().equals(meas2.getType()))
			return false;
		if (!meas1.getUnit().equals(meas2.getUnit()))
			return false;
		if (meas1.getValue() != meas2.getValue())
			return false;
		if (!(meas1.getPlant() == null && meas2.getPlant() == null)
				&& ((meas1.getPlant() == null && meas2.getPlant() != null)
						|| (meas1.getPlant() != null && meas2.getPlant() == null)
						|| (!meas1.getPlant().getId().equals(meas2.getPlant().getId()))))
			return false;
		if (!(meas1.getSensor() == null && meas2.getSensor() == null)
				&& ((meas1.getSensor() == null && meas2.getSensor() != null)
						|| (meas1.getSensor() != null && meas2.getSensor() == null)
						|| (!meas1.getSensor().getId().equals(meas2.getSensor().getId()))))
			return false;
		if (!(meas1.getPosition() == null && meas2.getPosition() == null)
				&& ((meas1.getPosition() == null && meas2.getPosition() != null)
						|| (meas1.getPosition() != null && meas2.getPosition() == null)
						|| (!meas1.getPosition().getId().equals(meas2.getPosition().getId()))))
			return false;

		return true;
	}
}
