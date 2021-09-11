package it.unifi.dinfo.swam.plantnursery.nosql.controller;

import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.GrowthPlaceByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.GrowthPlaceDto;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.GrowthPlaceByIdMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.model.GrowthPlaceById;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class GrowthPlaceController extends BaseController {
	
	@Inject
	private GrowthPlaceByIdDao growthPlaceByIdDao;
	
	@Inject
	private GrowthPlaceByIdMapper growthPlaceByIdMapper;
	
	public boolean deleteGrowthPlace(UUID idGrowthPlace) {
		this.cleanErrorFields();
		
		GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(idGrowthPlace);

		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return false;
		}

//		// If there are plants inside the growth place, it cannot be deleted
//		List<Plant> plants = plantDao.getPlantsByGrowthPlace(growthPlace);
//		if (plants.size() > 0) {
//			this.setErrorOccurred(true);
//			this.setErrorMessage("Cannot delete growth place: there are plants inside it");
//			return false;
//		}
//
//		// If there are sensors inside the growth place, it cannot be deleted
//		List<Sensor> sensors = sensorDao.getSensorsByGrowthplace(growthPlace);
//		if (sensors.size() > 0) {
//			this.setErrorOccurred(true);
//			this.setErrorMessage("Cannot delete growth place: there are sensors inside it");
//			return false;
//		}
//
//		// Delete all positions of the growth place
//		List<Position> positions = positionDao.getPositionsByGrowthPlace(growthPlace);
//		positionDao.deleteAll(positions);

		growthPlaceByIdDao.delete(idGrowthPlace);
		return true;
	}
	
	public GrowthPlaceDto getGrowthPlaceById(UUID id) {
		this.cleanErrorFields();
		
		GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(id); 
		
		if(growthPlace == null)
			return null;
		
		return growthPlaceByIdMapper.toDto(growthPlace);
	}

	public List<GrowthPlaceDto> getGrowthPlaces(String prefixName) {
		this.cleanErrorFields();
		
		List<GrowthPlaceById> growthPlaces; 
		if(prefixName == null)
			growthPlaces = growthPlaceByIdDao.getGrowthPlaces();
		else
			growthPlaces = growthPlaceByIdDao.getGrowthPlacesStartingByName(prefixName);
		
		List<GrowthPlaceDto> dtoList = growthPlaceByIdMapper.toDto(growthPlaces);
		return dtoList;
	}
	
	public boolean saveGrowthPlace(GrowthPlaceDto growthPlaceDto) {
		this.cleanErrorFields();
		
		if(growthPlaceDto == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Invalid growth place");
			return false;
		}
		
		// Check if there is already a growth place with the same name
		if (growthPlaceByIdDao.getGrowthPlaceByName(growthPlaceDto.getName()) != null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A growth place with the same name already exists");
			return false;
		}
		
		if(growthPlaceDto.getColumnsPositions() < 0 || growthPlaceDto.getRowsPositions() < 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The number of positions rows/columns cannot be negative");
			return false;
		}
		
//		List<Position> positions = new ArrayList<Position>();
//		if(growthPlaceDto.getColumnsPositions() > 0 && growthPlaceDto.getRowsPositions() > 0) {
//			for(int r = 0; r < growthPlaceDto.getRowsPositions(); r++) {
//				for(int c = 0; c < growthPlaceDto.getColumnsPositions(); c++) {
//					Position pos = ModelFactory.position();
//					pos.setRowIndex(r);
//					pos.setColumnIndex(c);
//					pos.setGrowthPlace(growthPlace);
//					
//					positions.add(pos);
//				}
//			}
//		}
		
		UUID id = Uuids.timeBased();
		GrowthPlaceById growthPlace = growthPlaceByIdMapper.toEntity(id, growthPlaceDto);
		growthPlaceByIdDao.save(growthPlace);
		
//		if(positions.size() > 0)
//			positionDao.saveAll(positions);
		
		return true;
	}
	
	public boolean updateGrowthPlace(UUID idGrowthPlace, GrowthPlaceDto growthPlaceDto) {
		this.cleanErrorFields();
		
		if(growthPlaceDto == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Invalid growth place");
			return false;
		}
		
		GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(idGrowthPlace); 
		
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
//		List<Position> newPositions = null;
//		List<Position> positions = positionDao.getPositionsByGrowthPlace(growthPlace);
//		int nRows = positions.stream().mapToInt(p -> p.getRowIndex()).max().getAsInt() + 1;
//		int nCols = positions.stream().mapToInt(p -> p.getColumnIndex()).max().getAsInt() + 1;
//		if(growthPlaceDto.getRowsPositions() != nRows || growthPlaceDto.getColumnsPositions() != nCols) {
//			if (positions.stream().anyMatch(p -> p.getPlant() != null)) {
//				this.setErrorOccurred(true);
//				this.setErrorMessage("Cannot change positions layout of the growth place: there are plants inside it");
//				return false;
//			}
//			
//			if (positions.stream().anyMatch(p -> p.getSensors() != null && p.getSensors().size() > 0)) {
//				this.setErrorOccurred(true);
//				this.setErrorMessage("Cannot change positions layout of the growth place: there are sensors inside it");
//				return false;
//			}
//
//			newPositions = new ArrayList<Position>();
//			for(int r = 0; r < growthPlaceDto.getRowsPositions(); r++) {
//				for(int c = 0; c < growthPlaceDto.getColumnsPositions(); c++) {
//					Position pos = ModelFactory.position();
//					pos.setRowIndex(r);
//					pos.setColumnIndex(c);
//					pos.setGrowthPlace(growthPlace);
//					
//					newPositions.add(pos);
//				}
//			}
//		}
		
		growthPlace = growthPlaceByIdMapper.toEntity(idGrowthPlace, growthPlaceDto);

		// Check if there is already another growth place with the same name
		GrowthPlaceById sameNameGrowthPlace = growthPlaceByIdDao.getGrowthPlaceByName(growthPlace.getName());
		if (sameNameGrowthPlace != null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A growth place with the same name already exists");
			return false;
		}

		growthPlaceByIdDao.update(growthPlace);
		
//		// Recreate positions layout if a change has been requested
//		if(newPositions != null) {
//			positionDao.deleteAll(positions);
//			positionDao.saveAll(newPositions);
//		}
		
		return true;
	}
}
