package it.unifi.dinfo.swam.plantnursery.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import it.unifi.dinfo.swam.plantnursery.model.FaultPeriod;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.MeasureType;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Sensor;
import it.unifi.dinfo.swam.plantnursery.utils.Utilities;

public class SensorDaoTest extends JpaTest {

	private SensorDao sensorDao;

	private final int N_SENSORS = 3;

	private String macAddress1 = "MAC-1";
	private String company1 = "company-1";
	private String model1 = "model-1";
	private String serialNumber1 = "serial-1";
	private LocalDate installationDate1 = LocalDate.parse("2020-08-17");
	private LocalDate disposalDate1 = null;
	private Set<MeasureType> measureTypes1 = Stream.of(MeasureType.SOIL_MOISTURE, MeasureType.SOIL_PH)
			.collect(Collectors.toSet());
	private List<FaultPeriod> faultPeriods1;
	private Sensor sensor1;
	
	// Fault periods for sensor1
	private LocalDateTime startFault1 = LocalDateTime.parse("2021-07-20T00:00:00");
	private LocalDateTime endFault1 = LocalDateTime.parse("2021-07-25T12:00:00");
	private FaultPeriod fp1;
	private LocalDateTime startFault2 = LocalDateTime.parse("2021-08-10T18:00:00");
	private LocalDateTime endFault2 = null;
	private FaultPeriod fp2;
	
	// Position for sensor1
	private Position position1;
	// Position for sensor1 sensor2
	private Position position2;
	// Position for sensor2
	private Position position3;
	private GrowthPlace growthPlace1;

	private String macAddress2 = "MAC-2";
	private String company2 = "company-2";
	private String model2 = "model-2";
	private String serialNumber2 = "serial-2";
	private LocalDate installationDate2 = LocalDate.parse("2020-08-15");
	private LocalDate disposalDate2 = null;
	private Set<MeasureType> measureTypes2 = Stream.of(MeasureType.SOIL_MOISTURE, MeasureType.SOIL_PH)
			.collect(Collectors.toSet());
	private List<FaultPeriod> faultPeriods2 = new ArrayList<FaultPeriod>();
	private Sensor sensor2;
	
	// Disposed sensor
	private String macAddress3 = "MAC-3";
	private String company3 = "company-3";
	private String model3 = "model-3";
	private String serialNumber3 = "serial-3";
	private LocalDate installationDate3 = LocalDate.parse("2020-06-15");
	private LocalDate disposalDate3 = LocalDate.parse("2021-08-15");
	private Set<MeasureType> measureTypes3 = Stream.of(MeasureType.TEMPERATURE).collect(Collectors.toSet());
	private List<FaultPeriod> faultPeriods3 = new ArrayList<FaultPeriod>();
	private Sensor sensor3;

	@Override
	protected void init() throws IllegalAccessException {
		sensorDao = new SensorDao(this.entityManager);

		sensor1 = ModelFactory.sensor();
		sensor1.setMacAddress(macAddress1);
		sensor1.setCompany(company1);
		sensor1.setModel(model1);
		sensor1.setSerialNumber(serialNumber1);
		sensor1.setInstallationDate(installationDate1);
		sensor1.setDisposalDate(disposalDate1);
		sensor1.setMeasureTypes(measureTypes1);
		fp1 = new FaultPeriod();
		fp1.setStartDate(startFault1);
		fp1.setEndDate(endFault1);
		fp2 = new FaultPeriod();
		fp2.setStartDate(startFault2);
		fp2.setEndDate(endFault2);
		faultPeriods1 = Stream.of(fp1, fp2).collect(Collectors.toList());
		sensor1.setFaultPeriods(faultPeriods1);

		sensor2 = ModelFactory.sensor();
		sensor2.setMacAddress(macAddress2);
		sensor2.setCompany(company2);
		sensor2.setModel(model2);
		sensor2.setSerialNumber(serialNumber2);
		sensor2.setInstallationDate(installationDate2);
		sensor2.setDisposalDate(disposalDate2);
		sensor2.setMeasureTypes(measureTypes2);
		sensor2.setFaultPeriods(faultPeriods2);
		
		sensor3 = ModelFactory.sensor();
		sensor3.setMacAddress(macAddress3);
		sensor3.setCompany(company3);
		sensor3.setModel(model3);
		sensor3.setSerialNumber(serialNumber3);
		sensor3.setInstallationDate(installationDate3);
		sensor3.setDisposalDate(disposalDate3);
		sensor3.setMeasureTypes(measureTypes3);
		sensor3.setFaultPeriods(faultPeriods3);
		
		growthPlace1 = ModelFactory.growthPlace();
		position1 = ModelFactory.position();
		position1.setGrowthPlace(growthPlace1);
		position1.addSensor(sensor1);
		position2 = ModelFactory.position();
		position2.setGrowthPlace(growthPlace1);
		position2.addSensor(sensor1);
		position2.addSensor(sensor2);
		position3 = ModelFactory.position();
		position3.setGrowthPlace(growthPlace1);
		position3.addSensor(sensor2);
		
		this.entityManager.persist(growthPlace1);
		this.entityManager.persist(position1);
		this.entityManager.persist(position2);
		this.entityManager.persist(position3);
		this.entityManager.persist(sensor1);
		this.entityManager.persist(sensor2);
		this.entityManager.persist(sensor3);
	}

	// ---- Testing BaseDAO methods

	@Test
	public void testDelete() {
		sensorDao.delete(sensor1);

		Sensor retrievedSensor = this.entityManager.find(Sensor.class, sensor1.getId());

		assertNull(retrievedSensor);
	}

	@Test
	public void testFindById() {
		Sensor retrievedSensor = sensorDao.findById(sensor1.getId());

		assertEquals(true, this.areSensorsEqual(retrievedSensor, sensor1));
	}

	@Test
	public void testFindByIdWithWrongId() {
		Sensor retrievedSensor = sensorDao.findById(0L);

		assertNull(retrievedSensor);
	}

	@Test
	public void testSave() {
		Sensor sensor = ModelFactory.sensor();
		sensor.setMacAddress("MAC-new");
		sensor.setCompany("company-new");
		sensor.setModel("model-new");
		sensor.setSerialNumber("serial-new");
		sensor.setInstallationDate(LocalDate.parse("2020-09-01"));
		sensor.setDisposalDate(LocalDate.parse("2020-11-01"));
		sensor.setMeasureTypes(Stream.of(MeasureType.LIGHT, MeasureType.TEMPERATURE).collect(Collectors.toSet()));
		FaultPeriod fp = new FaultPeriod();
		fp.setStartDate(LocalDateTime.parse("2020-10-01T10:00:00"));
		fp.setEndDate(LocalDateTime.parse("2020-10-15T11:00:00"));
		sensor.setFaultPeriods(Stream.of(fp).collect(Collectors.toList()));
		sensorDao.save(sensor);

		Sensor retrievedSensor = this.entityManager.find(Sensor.class, sensor.getId());

		assertEquals(true, this.areSensorsEqual(sensor, retrievedSensor));
	}

	@Test
	public void testUpdate() {
		sensor1.setMacAddress("MAC-update");
		sensor1.setCompany("company-update");
		sensor1.setModel("model-update");
		sensor1.setSerialNumber("serial-update");
		sensor1.setInstallationDate(LocalDate.parse("2020-09-01"));
		sensor1.setDisposalDate(LocalDate.parse("2020-11-01"));
		sensor1.setMeasureTypes(Stream.of(MeasureType.LIGHT, MeasureType.TEMPERATURE).collect(Collectors.toSet()));
		FaultPeriod fp = new FaultPeriod();
		fp.setStartDate(LocalDateTime.parse("2020-10-01T16:00:00"));
		fp.setEndDate(LocalDateTime.parse("2020-10-15T17:00:00"));
		sensor1.setFaultPeriods(Stream.of(fp).collect(Collectors.toList()));
		sensorDao.update(sensor1);

		Sensor retrievedSensor = this.entityManager.find(Sensor.class, sensor1.getId());

		assertEquals(true, this.areSensorsEqual(sensor1, retrievedSensor));
	}
	
	// ----- Testing GrowthPlaceDao methods
	
	@Test
	void testGetSensors() {
		List<Sensor> sensors = sensorDao.getSensors();		
		
		assertEquals(N_SENSORS, sensors.size());
		assertEquals(true, sensors.stream().anyMatch(p -> areSensorsEqual(p, sensor1)));
		assertEquals(true, sensors.stream().anyMatch(p -> areSensorsEqual(p, sensor2)));
		assertEquals(true, sensors.stream().anyMatch(p -> areSensorsEqual(p, sensor3)));
	}
	
	@Test
	void testGetSensorsByGrowthPlace() {
		List<Sensor> sensors = sensorDao.getSensorsByGrowthplace(growthPlace1);		
		
		assertEquals(2, sensors.size());
		assertEquals(true, sensors.stream().anyMatch(p -> areSensorsEqual(p, sensor1)));
		assertEquals(true, sensors.stream().anyMatch(p -> areSensorsEqual(p, sensor2)));
	}
	
	@Test
	void testGetSensorsByCompany() {
		List<Sensor> sensors = sensorDao.getSensorsByCompany(company1);		
		
		assertEquals(1, sensors.size());
		assertEquals(true, sensors.stream().anyMatch(p -> areSensorsEqual(p, sensor1)));
	}
	
	@Test
	void testGetSensorsByMacAddress() {
		Sensor sensor = sensorDao.getSensorByMacAddress(macAddress1);
		assertEquals(true, areSensorsEqual(sensor, sensor1));
	}
	
	@Test
	void testGetSensorsByMacAddressWhenWrongMacAddress() {
		Sensor sensor = sensorDao.getSensorByMacAddress("mac-wrong");
		assertNull(sensor);
	}
	
	@Test
	void testGetSensorsByModel() {
		List<Sensor> sensors = sensorDao.getSensorsByModel(model2);		
		
		assertEquals(1, sensors.size());
		assertEquals(true, sensors.stream().anyMatch(p -> areSensorsEqual(p, sensor2)));
	}
	
	@Test
	void testGetDisposedSensors() {
		List<Sensor> sensors = sensorDao.getDisposedSensors();		
		
		assertEquals(1, sensors.size());
		assertEquals(true, sensors.stream().anyMatch(p -> areSensorsEqual(p, sensor3)));
	}
	
	@Test
	void testGetActiveSensors() {
		List<Sensor> sensors = sensorDao.getActiveSensors();		
		
		assertEquals(2, sensors.size());
		assertEquals(true, sensors.stream().anyMatch(p -> areSensorsEqual(p, sensor1)));
		assertEquals(true, sensors.stream().anyMatch(p -> areSensorsEqual(p, sensor2)));
	}

	// Method for field-based equality check between Sensor entities
	private boolean areSensorsEqual(Sensor sensor1, Sensor sensor2) {
		if (sensor1 == null || sensor2 == null)
			return false;
		if (!sensor1.getId().equals(sensor2.getId()))
			return false;
		if (!sensor1.getUuid().equals(sensor2.getUuid()))
			return false;
		if (!sensor1.getMacAddress().equals(sensor2.getMacAddress()))
			return false;
		if (!sensor1.getCompany().equals(sensor2.getCompany()))
			return false;
		if (!sensor1.getModel().equals(sensor2.getModel()))
			return false;
		if (!sensor1.getSerialNumber().equals(sensor2.getSerialNumber()))
			return false;
		if (!sensor1.getInstallationDate().equals(sensor2.getInstallationDate()))
			return false;
		if (!Utilities.areDateEqualsOrBothNull(sensor1.getDisposalDate(), sensor2.getDisposalDate()))
			return false;
		if (!sensor1.getMeasureTypes().equals(sensor2.getMeasureTypes()))
			return false;

		if (sensor1.getFaultPeriods().size() != sensor2.getFaultPeriods().size())
			return false;

		for (int i = 0; i < sensor1.getFaultPeriods().size(); i++) {
			FaultPeriod fp1 = sensor1.getFaultPeriods().get(i);
			FaultPeriod fp2 = sensor2.getFaultPeriods().get(i);

			if (!fp1.getStartDate().equals(fp2.getStartDate()))
				return false;
			if (!Utilities.areDateEqualsOrBothNull(fp1.getEndDate(), fp2.getEndDate()))				
				return false;
		}

		return true;
	}

}
