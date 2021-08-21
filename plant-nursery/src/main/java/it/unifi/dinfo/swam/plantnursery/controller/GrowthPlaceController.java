package it.unifi.dinfo.swam.plantnursery.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.GrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.dao.PlantDao;
import it.unifi.dinfo.swam.plantnursery.dao.PositionDao;
import it.unifi.dinfo.swam.plantnursery.dao.SensorDao;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Sensor;

@RequestScoped
public class GrowthPlaceController {

	@Inject
	private GrowthPlaceDao growthPlaceDao;

	@Inject
	private PositionDao positionDao;

	@Inject
	private PlantDao plantDao;

	@Inject
	private SensorDao sensorDao;

	public boolean addPlantToPosition(Long idPlant, Long idPosition) {
		Position position = positionDao.findById(idPosition);

		if (position == null)
			return false;

		if (position.getPlant() != null)
			return false;

		Plant plant = plantDao.findById(idPlant);

		if (plant == null)
			return false;

		position.setPlant(plant);
		positionDao.update(position);

		return true;
	}

	public boolean addSensorToPosition(Long idSensor, Long idPosition) {
		Position position = positionDao.findById(idPosition);

		if (position == null)
			return false;

		Sensor sensor = sensorDao.findById(idSensor);

		if (sensor == null)
			return false;

		// Check if the sensor is already placed in this position
		List<Sensor> placedSensors = position.getSensors();
		for (Sensor s : placedSensors) {
			if (s.equals(sensor))
				return false;
		}

		position.addSensor(sensor);
		positionDao.update(position);

		return true;
	}

	public boolean deleteGrowthPlace(Long idGrowthPlace) {
		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);

		if (growthPlace == null)
			return false;

		// If there are plants inside the growth place, it cannot be deleted
		List<Plant> plants = plantDao.getPlantsByGrowthPlace(growthPlace);
		if (plants.size() > 0)
			return false;

		// If there are sensors inside the growth place, it cannot be deleted
		List<Sensor> sensors = sensorDao.getSensorsByGrowthplace(growthPlace);
		if (sensors.size() > 0)
			return false;

		// Delete all positions of the growth place
		List<Position> positions = positionDao.getPositionsByGrowthPlace(growthPlace);
		positionDao.deleteAll(positions);

		growthPlaceDao.delete(growthPlace);
		return true;
	}

	public GrowthPlace getGrowthPlaceById(Long id) {
		return growthPlaceDao.findById(id);
	}

	public List<GrowthPlace> getGrowthPlaces() {
		return growthPlaceDao.getGrowthPlaces();
	}

	public List<GrowthPlace> getGrowthPlacesByName(String prefixName) {
		return growthPlaceDao.getGrowthPlacesStartingByName(prefixName);
	}

	public List<Position> getPositionsByGrowthPlace(Long idGrowthPlace) {
		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);

		if (growthPlace == null)
			return new ArrayList<>();

		return positionDao.getPositionsByGrowthPlace(growthPlace);
	}

	public List<Position> getPlantFreePositionsByGrowthPlace(Long idGrowthPlace) {
		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);

		if (growthPlace == null)
			return new ArrayList<>();

		return positionDao.getPlantFreePositionsByGrowthPlace(growthPlace);
	}

	public boolean removePlantFromPosition(Long idPosition) {
		Position position = positionDao.findById(idPosition);

		if (position == null)
			return false;

		if (position.getPlant() == null)
			return false;

		position.setPlant(null);
		return true;
	}

	public boolean removeSensorFromPosition(Long idSensor, Long idPosition) {
		Position position = positionDao.findById(idPosition);

		if (position == null)
			return false;

		Sensor sensor = sensorDao.findById(idSensor);

		if (sensor == null)
			return false;

		// Check if the sensor is actually placed in this position
		boolean removed = position.removeSensor(sensor);
		if (!removed)
			return false;

		positionDao.update(position);

		return true;
	}

	public boolean saveGrowthPlace(GrowthPlace growthPlace) {
		// Check if there is already a growth place with the same name
		if (growthPlaceDao.getGrowthPlaceByName(growthPlace.getName()) != null)
			return false;

		growthPlaceDao.save(growthPlace);
		return true;
	}

	public boolean setGrowthPlacePositionsLayout(Long idGrowthPlace, int rows, int cols) {
		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);

		if (growthPlace == null)
			return false;

		// If there are plants inside the growth place, layout cannot be changed
		List<Plant> plants = plantDao.getPlantsByGrowthPlace(growthPlace);
		if (plants.size() > 0)
			return false;

		List<Position> positions = positionDao.getPositionsByGrowthPlace(growthPlace);
		positionDao.deleteAll(positions);

		List<Position> newPositions = new ArrayList<Position>();
		positionDao.saveAll(newPositions);

		return true;
	}

	public boolean updateGrowthPlace(GrowthPlace growthPlace) {
		if (growthPlaceDao.findById(growthPlace.getId()) == null)
			return false;

		// Check if there is already another growth place with the same name
		GrowthPlace sameNameGrowthPlace = growthPlaceDao.getGrowthPlaceByName(growthPlace.getName());
		if (sameNameGrowthPlace != null)
			return false;

		growthPlaceDao.update(growthPlace);
		return true;
	}
}
