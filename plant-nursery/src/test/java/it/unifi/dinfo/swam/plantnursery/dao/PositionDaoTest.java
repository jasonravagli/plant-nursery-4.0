package it.unifi.dinfo.swam.plantnursery.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Sensor;

public class PositionDaoTest extends JpaTest {

	private PositionDao positionDao;

	private GrowthPlace growthPlace1;
	private int rowIndex1 = 0;
	private int colIndex1 = 0;
	private Position position1;

	private Plant plant2;
	private int rowIndex2 = 0;
	private int colIndex2 = 1;
	private Position position2;

	private GrowthPlace growthPlace3;
	private int rowIndex3 = 0;
	private int colIndex3 = 0;
	private Position position3;
	
	// Sensor for position1
	private Sensor sensor1;
	// Sensor for position1 and position2
	private Sensor sensor2;
	// Sensor for position2
	private Sensor sensor3;

	@Override
	protected void init() throws IllegalAccessException {
		positionDao = new PositionDao(this.entityManager);

		growthPlace1 = FakeModelFactory.growthPlace();
		growthPlace3 = FakeModelFactory.growthPlace();
		
		plant2 = FakeModelFactory.plant();

		position1 = ModelFactory.position();
		position1.setRowIndex(rowIndex1);
		position1.setColumnIndex(colIndex1);
		position1.setGrowthPlace(growthPlace1);
		
		position2 = ModelFactory.position();
		position2.setRowIndex(rowIndex2);
		position2.setColumnIndex(colIndex2);
		position2.setGrowthPlace(growthPlace1);
		position2.setPlant(plant2);

		position3 = ModelFactory.position();
		position3.setRowIndex(rowIndex3);
		position3.setColumnIndex(colIndex3);
		position3.setGrowthPlace(growthPlace3);
		
		sensor1 = ModelFactory.sensor();
		sensor2 = ModelFactory.sensor();
		sensor3 = ModelFactory.sensor();
		position1.addSensor(sensor1);
		position1.addSensor(sensor2);
		position2.addSensor(sensor2);
		position2.addSensor(sensor3);
		
		this.entityManager.persist(sensor1);
		this.entityManager.persist(sensor2);
		this.entityManager.persist(sensor3);
		this.entityManager.persist(plant2.getSpecies());
		this.entityManager.persist(plant2);
		this.entityManager.persist(growthPlace1);
		this.entityManager.persist(growthPlace3);
		this.entityManager.persist(position1);
		this.entityManager.persist(position2);
		this.entityManager.persist(position3);
	}

	// ---- Testing BaseDAO methods

	@Test
	public void testDelete() {
		positionDao.delete(position1);

		Position retrievedPos = this.entityManager.find(Position.class, position1.getId());
		assertNull(retrievedPos);
	}
	
	@Test
	public void testDeleteAll() {
		positionDao.deleteAll(Stream.of(position1, position2).collect(Collectors.toList()));

		Position retrievedPos = this.entityManager.find(Position.class, position1.getId());
		assertNull(retrievedPos);
		retrievedPos = this.entityManager.find(Position.class, position2.getId());
		assertNull(retrievedPos);
	}

	@Test
	public void testFindById() {
		Position retrievedPos = positionDao.findById(position1.getId());

		assertEquals(true, this.arePositionsEqual(retrievedPos, position1));
	}

	@Test
	public void testFindByIdWithWrongId() {
		Position retrievedPos = positionDao.findById(0L);

		assertNull(retrievedPos);
	}

	@Test
	public void testSave() {
		Position pos = ModelFactory.position();
		pos.setRowIndex(10);
		pos.setColumnIndex(11);
		pos.setGrowthPlace(growthPlace1);
		positionDao.save(pos);

		Position retrievedPos = this.entityManager.find(Position.class, pos.getId());

		assertEquals(true, this.arePositionsEqual(retrievedPos, pos));
	}
	
	@Test
	public void testSaveAll() {
		Position newPos1 = ModelFactory.position();
		newPos1.setRowIndex(10);
		newPos1.setColumnIndex(11);
		newPos1.setGrowthPlace(growthPlace1);
		
		Position newPos2 = ModelFactory.position();
		newPos2.setRowIndex(10);
		newPos2.setColumnIndex(11);
		newPos2.setGrowthPlace(growthPlace1);
		positionDao.saveAll(Stream.of(newPos1, newPos2).collect(Collectors.toList()));

		Position retrievedPos = this.entityManager.find(Position.class, newPos1.getId());
		assertEquals(true, this.arePositionsEqual(retrievedPos, newPos1));
		retrievedPos = this.entityManager.find(Position.class, newPos2.getId());
		assertEquals(true, this.arePositionsEqual(retrievedPos, newPos2));
	}

	@Test
	public void testUpdate() {
		position1.setRowIndex(20);
		position1.setColumnIndex(21);
		position1.setGrowthPlace(growthPlace3);
		positionDao.update(position1);

		Position retrievedPos = this.entityManager.find(Position.class, position1.getId());

		assertEquals(true, this.arePositionsEqual(retrievedPos, position1));
	}
	
	@Test
	public void testUpdateAll() {
		position1.setRowIndex(30);
		position1.setColumnIndex(31);
		position1.setGrowthPlace(growthPlace3);
		position1.removeSensor(sensor2);
		
		position2.setRowIndex(40);
		position2.setColumnIndex(41);
		position2.setGrowthPlace(growthPlace3);
		position2.removeSensor(sensor2);
		positionDao.updateAll(Stream.of(position1, position2).collect(Collectors.toList()));

		Position retrievedPos = this.entityManager.find(Position.class, position1.getId());
		assertEquals(true, this.arePositionsEqual(retrievedPos, position1));
		retrievedPos = this.entityManager.find(Position.class, position2.getId());
		assertEquals(true, this.arePositionsEqual(retrievedPos, position2));
	}

	// ----- Testing PositionDao methods

	@Test
	public void testGetPositionsByGrowthPlace() {
		List<Position> retrievedPositions = positionDao.getPositionsByGrowthPlace(growthPlace1);

		assertEquals(2, retrievedPositions.size());
		assertEquals(true, retrievedPositions.stream().anyMatch(p -> arePositionsEqual(p, position1)));
		assertEquals(true, retrievedPositions.stream().anyMatch(p -> arePositionsEqual(p, position2)));
	}
	
	@Test
	public void testGetPositionByPlant() {
		Position retrievedPosition = positionDao.getPositionByPlant(plant2);

		assertEquals(true, arePositionsEqual(retrievedPosition, position2));
	}
	
	@Test
	public void testGetPositionByPlantWhenPlantHasNoPosition() {
		Plant plant = FakeModelFactory.plant();
		this.entityManager.persist(plant.getSpecies());
		this.entityManager.persist(plant);
		
		Position retrievedPosition = positionDao.getPositionByPlant(plant);

		assertNull(retrievedPosition);
	}
	
	@Test
	public void testGetPlantFreePositions() {
		List<Position> positions = positionDao.getPlantFreePositions();

		assertEquals(2, positions.size());
		assertEquals(true, positions.stream().anyMatch(p -> arePositionsEqual(p, position1)));
		assertEquals(true, positions.stream().anyMatch(p -> arePositionsEqual(p, position3)));
	}
	
	@Test
	public void testGetPlantFreePositionsByGrowthPlace() {
		List<Position> positions = positionDao.getPlantFreePositionsByGrowthPlace(growthPlace1);

		assertEquals(1, positions.size());
		assertEquals(true, positions.stream().anyMatch(p -> arePositionsEqual(p, position1)));
	}
	
	@Test
	public void testGetPositionsBySensor() {
		List<Position> positions = positionDao.getPositionsBySensor(sensor2);

		assertEquals(2, positions.size());
		assertEquals(true, positions.stream().anyMatch(p -> arePositionsEqual(p, position1)));
		assertEquals(true, positions.stream().anyMatch(p -> arePositionsEqual(p, position2)));
	}

	// Method for field-based equality check between Position entities
	private boolean arePositionsEqual(Position pos1, Position pos2) {
		if (pos1 == null || pos2 == null)
			return false;
		if (!pos1.getId().equals(pos2.getId()))
			return false;
		if (!pos1.getUuid().equals(pos2.getUuid()))
			return false;
		if (pos1.getRowIndex() != pos2.getRowIndex())
			return false;
		if (pos1.getColumnIndex() != pos2.getColumnIndex())
			return false;
		if (!pos1.getGrowthPlace().getId().equals(pos2.getGrowthPlace().getId()))
			return false;
		if (!(pos1.getPlant() == null && pos2.getPlant() == null)
				&& ((pos1.getPlant() == null && pos2.getPlant() != null)
						|| (pos1.getPlant() != null && pos2.getPlant() == null)
						|| (!pos1.getPlant().getId().equals(pos2.getPlant().getId()))))
			return false;
		
		if(pos1.getSensors().size() != pos2.getSensors().size())
			return false;
		
		for(int i = 0; i < pos1.getSensors().size(); i++) {
			Sensor s1 = pos1.getSensors().get(i);
			Sensor s2 = pos2.getSensors().get(i);
			
			if(!s1.getId().equals(s2.getId()))
				return false;
		}

		return true;
	}
}
