package it.unifi.dinfo.swam.plantnursery.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.GrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.dao.PlantDao;
import it.unifi.dinfo.swam.plantnursery.dao.PositionDao;
import it.unifi.dinfo.swam.plantnursery.dao.SensorDao;
import it.unifi.dinfo.swam.plantnursery.dto.GrowthPlaceDto;
import it.unifi.dinfo.swam.plantnursery.mapper.GrowthPlaceMapper;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Sensor;

@RequestScoped
public class GrowthPlaceController extends BaseController {

	@Inject
	private GrowthPlaceDao growthPlaceDao;

	@Inject
	private PositionDao positionDao;

	@Inject
	private PlantDao plantDao;

	@Inject
	private SensorDao sensorDao;
	
	@Inject
	private GrowthPlaceMapper growthPlaceMapper;

//	public boolean addPlantToPosition(Long idPlant, Long idPosition) {
//		Position position = positionDao.findById(idPosition);
//
//		if (position == null)
//			return false;
//
//		if (position.getPlant() != null)
//			return false;
//
//		Plant plant = plantDao.findById(idPlant);
//
//		if (plant == null)
//			return false;
//
//		position.setPlant(plant);
//		positionDao.update(position);
//
//		return true;
//	}
//
//	public boolean addSensorToPosition(Long idSensor, Long idPosition) {
//		Position position = positionDao.findById(idPosition);
//
//		if (position == null)
//			return false;
//
//		Sensor sensor = sensorDao.findById(idSensor);
//
//		if (sensor == null)
//			return false;
//
//		// Check if the sensor is already placed in this position
//		List<Sensor> placedSensors = position.getSensors();
//		for (Sensor s : placedSensors) {
//			if (s.equals(sensor))
//				return false;
//		}
//
//		position.addSensor(sensor);
//		positionDao.update(position);
//
//		return true;
//	}

	public boolean deleteGrowthPlace(Long idGrowthPlace) {
		this.cleanErrorFields();
		
		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);

		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return false;
		}

		// If there are plants inside the growth place, it cannot be deleted
		List<Plant> plants = plantDao.getPlantsByGrowthPlace(growthPlace);
		if (plants.size() > 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Cannot delete growth place: there are plants inside it");
			return false;
		}

		// If there are sensors inside the growth place, it cannot be deleted
		List<Sensor> sensors = sensorDao.getSensorsByGrowthplace(growthPlace);
		if (sensors.size() > 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Cannot delete growth place: there are sensors inside it");
			return false;
		}

		// Delete all positions of the growth place
		List<Position> positions = positionDao.getPositionsByGrowthPlace(growthPlace);
		positionDao.deleteAll(positions);

		growthPlaceDao.delete(growthPlace);
		return true;
	}

	public GrowthPlaceDto getGrowthPlaceById(Long id) {
		this.cleanErrorFields();
		
		GrowthPlace growthPlace = growthPlaceDao.findById(id); 
		
		return growthPlace == null? null : growthPlaceMapper.toDto(growthPlace);
	}

	public List<GrowthPlaceDto> getGrowthPlaces(String prefixName) {
		this.cleanErrorFields();
		
		List<GrowthPlace> growthPlaces; 
		if(prefixName == null)
			growthPlaces = growthPlaceDao.getGrowthPlaces();
		else
			growthPlaces = growthPlaceDao.getGrowthPlacesStartingByName(prefixName);
		
		List<GrowthPlaceDto> dtoList = new ArrayList<GrowthPlaceDto>();
		for (int i = 0; i < growthPlaces.size(); i++) {
			GrowthPlace growthPlace = growthPlaces.get(i);
			List<Position> positions = positionDao.getPositionsByGrowthPlace(growthPlace);
			dtoList.add(growthPlaceMapper.toDto(growthPlace, positions));
        }
		return dtoList;
	}

//	public boolean removePlantFromPosition(Long idPosition) {
//		Position position = positionDao.findById(idPosition);
//
//		if (position == null)
//			return false;
//
//		if (position.getPlant() == null)
//			return false;
//
//		position.setPlant(null);
//		return true;
//	}
//
//	public boolean removeSensorFromPosition(Long idSensor, Long idPosition) {
//		Position position = positionDao.findById(idPosition);
//
//		if (position == null)
//			return false;
//
//		Sensor sensor = sensorDao.findById(idSensor);
//
//		if (sensor == null)
//			return false;
//
//		// Check if the sensor is actually placed in this position
//		boolean removed = position.removeSensor(sensor);
//		if (!removed)
//			return false;
//
//		positionDao.update(position);
//
//		return true;
//	}

	public boolean saveGrowthPlace(GrowthPlaceDto growthPlaceDto) {
		this.cleanErrorFields();
		
		if(growthPlaceDto == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Invalid growth place");
			return false;
		}
		
		GrowthPlace growthPlace = growthPlaceMapper.toEntity(growthPlaceDto);
		
		// Check if there is already a growth place with the same name
		if (growthPlaceDao.getGrowthPlaceByName(growthPlace.getName()) != null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A growth place with the same name already exists");
			return false;
		}
		
		if(growthPlaceDto.getColumnsPositions() < 0 || growthPlaceDto.getRowsPositions() < 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The number of positions rows/columns cannot be negative");
			return false;
		}
		
		List<Position> positions = new ArrayList<Position>();
		if(growthPlaceDto.getRowsPositions() > 0 && growthPlaceDto.getRowsPositions() > 0) {
			for(int r = 0; r < growthPlaceDto.getRowsPositions(); r++) {
				for(int c = 0; c < growthPlaceDto.getRowsPositions(); c++) {
					Position pos = ModelFactory.position();
					pos.setRowIndex(r);
					pos.setColumnIndex(c);
					pos.setGrowthPlace(growthPlace);
					
					positions.add(pos);
				}
			}
		}

		growthPlaceDao.save(growthPlace);
		
		if(positions.size() > 0)
			positionDao.saveAll(positions);
		
		return true;
	}

	public boolean updateGrowthPlace(GrowthPlaceDto growthPlaceDto) {
		this.cleanErrorFields();
		
		if(growthPlaceDto == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Invalid growth place");
			return false;
		}
		
		GrowthPlace growthPlace = growthPlaceDao.findById(growthPlaceDto.getId()); 
		
		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return false;
		}
		
		if(growthPlaceDto.getColumnsPositions() < 0 || growthPlaceDto.getRowsPositions() < 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The number of positions rows/columns cannot be negative");
			return false;
		}
		
		// Check if positions layout is changed. If there are sensors or plants inside the growth place it cannot be updated
		List<Position> newPositions = null;
		List<Position> positions = positionDao.getPositionsByGrowthPlace(growthPlace);
		int nRows = positions.stream().mapToInt(p -> p.getRowIndex()).max().getAsInt() + 1;
		int nCols = positions.stream().mapToInt(p -> p.getColumnIndex()).max().getAsInt() + 1;
		if(growthPlaceDto.getRowsPositions() != nRows || growthPlaceDto.getColumnsPositions() != nCols) {
			if (positions.stream().anyMatch(p -> p.getPlant() != null)) {
				this.setErrorOccurred(true);
				this.setErrorMessage("Cannot change positions layout of the growth place: there are plants inside it");
				return false;
			}
			
			if (positions.stream().anyMatch(p -> p.getSensors() != null && p.getSensors().size() > 0)) {
				this.setErrorOccurred(true);
				this.setErrorMessage("Cannot change positions layout of the growth place: there are sensors inside it");
				return false;
			}

			newPositions = new ArrayList<Position>();
			for(int r = 0; r < growthPlaceDto.getRowsPositions(); r++) {
				for(int c = 0; c < growthPlaceDto.getRowsPositions(); c++) {
					Position pos = ModelFactory.position();
					pos.setRowIndex(r);
					pos.setColumnIndex(c);
					pos.setGrowthPlace(growthPlace);
					
					newPositions.add(pos);
				}
			}
		}
		
		growthPlace = growthPlaceMapper.updateEntity(growthPlace, growthPlaceDto);

		// Check if there is already another growth place with the same name
		GrowthPlace sameNameGrowthPlace = growthPlaceDao.getGrowthPlaceByName(growthPlace.getName());
		if (sameNameGrowthPlace != null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A growth place with the same name already exists");
			return false;
		}

		growthPlaceDao.update(growthPlace);
		
		// Recreate positions layout if a change has been requested
		if(newPositions != null) {
			positionDao.deleteAll(positions);
			positionDao.saveAll(newPositions);
		}
		
		return true;
	}
}
