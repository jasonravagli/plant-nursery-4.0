package it.unifi.dinfo.swam.plantnursery.nosql.controller;

import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.GrowthPlaceByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.plant.PlantByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByPlantDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionBySensorDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.sensor.SensorByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.GrowthPlaceDto;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.PositionDto;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.GrowthPlaceByIdMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.PositionMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.model.GrowthPlaceById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.PlantByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.SensorByGrowthPlace;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class GrowthPlaceController extends BaseController {

	@Inject
	private GrowthPlaceByIdDao growthPlaceByIdDao;

	@Inject
	private PlantByGrowthPlaceDao plantByGrowthPlaceDao;

	@Inject
	private PositionByIdDao positionByIdDao;

	@Inject
	private PositionByGrowthPlaceDao positionByGrowthPlaceDao;

	@Inject
	private PositionByPlantDao positionByPlantDao;

	@Inject
	private PositionBySensorDao positionBySensorDao;

	@Inject
	private SensorByGrowthPlaceDao sensorByGrowthPlaceDao;

	@Inject
	private GrowthPlaceByIdMapper growthPlaceByIdMapper;

	@Inject
	private PositionMapper positionMapper;

	public boolean deleteGrowthPlace(UUID idGrowthPlace) {
		this.cleanErrorFields();

		GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(idGrowthPlace);

		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return false;
		}

		// If there are plants inside the growth place, it cannot be deleted
		List<PlantByGrowthPlace> plants = plantByGrowthPlaceDao.getPlantsByGp(idGrowthPlace, null, null);
		if (plants.size() > 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Cannot delete growth place: there are plants inside it");
			return false;
		}

		// If there are sensors inside the growth place, it cannot be deleted
		List<SensorByGrowthPlace> sensors = sensorByGrowthPlaceDao.getSensorsByGp(idGrowthPlace);
		if (sensors.size() > 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Cannot delete growth place: there are sensors inside it");
			return false;
		}

		// Delete all positions of the growth place
		List<PositionByGrowthPlace> positions = positionByGrowthPlaceDao.getPositionsByGrowthPlace(idGrowthPlace);
		for (PositionByGrowthPlace position : positions) {
			positionByIdDao.delete(position.getId());
			positionByGrowthPlaceDao.delete(position.getGrowthPlaceId(), position.isFree(), position.getId());
			positionByPlantDao.delete(position.getIdPlant(), position.getId());

			for (UUID idSensor : position.getListSensors()) {
				positionBySensorDao.delete(idSensor, position.getId());
			}
		}

		growthPlaceByIdDao.delete(idGrowthPlace);
		return true;
	}

	public GrowthPlaceDto getGrowthPlaceById(UUID id) {
		this.cleanErrorFields();

		GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(id);

		if (growthPlace == null)
			return null;

		return growthPlaceByIdMapper.toDto(growthPlace);
	}

	public List<GrowthPlaceDto> getGrowthPlaces(String prefixName) {
		this.cleanErrorFields();

		List<GrowthPlaceById> growthPlaces;
		if (prefixName == null)
			growthPlaces = growthPlaceByIdDao.getGrowthPlaces();
		else
			growthPlaces = growthPlaceByIdDao.getGrowthPlacesStartingByName(prefixName);

		List<GrowthPlaceDto> dtoList = growthPlaceByIdMapper.toDto(growthPlaces);
		return dtoList;
	}

	public boolean saveGrowthPlace(GrowthPlaceDto growthPlaceDto) {
		this.cleanErrorFields();

		if (growthPlaceDto == null) {
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

		if (growthPlaceDto.getColumnsPositions() < 0 || growthPlaceDto.getRowsPositions() < 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The number of positions rows/columns cannot be negative");
			return false;
		}

		UUID id = Uuids.timeBased();
		GrowthPlaceById growthPlace = growthPlaceByIdMapper.toEntity(id, growthPlaceDto);
		growthPlaceByIdDao.save(growthPlace);

		try {
			if (growthPlaceDto.getColumnsPositions() > 0 && growthPlaceDto.getRowsPositions() > 0) {
				for (int r = 0; r < growthPlaceDto.getRowsPositions(); r++) {
					for (int c = 0; c < growthPlaceDto.getColumnsPositions(); c++) {
						UUID idPosition = Uuids.timeBased();

						PositionDto positionDto = new PositionDto();
						positionDto.setRowIndex(r);
						positionDto.setColIndex(c);
						positionDto.setFree(true);
						positionDto.setIdGrowthPlace(growthPlace.getId());
						positionDto.setGrowthPlaceName(growthPlace.getName());

						PositionById positionById = positionMapper.toEntity(idPosition, positionDto,
								PositionById.class);
						PositionByGrowthPlace positionByGrowthPlace = positionMapper.toEntity(idPosition, positionDto,
								PositionByGrowthPlace.class);

						positionByIdDao.save(positionById);
						positionByGrowthPlaceDao.save(positionByGrowthPlace);
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			this.setErrorOccurred(true);
			this.setErrorMessage("An error occured during the conversion from entities to dtos");
			return false;
		}

		return true;
	}

	public boolean updateGrowthPlace(UUID idGrowthPlace, GrowthPlaceDto growthPlaceDto) {
		this.cleanErrorFields();

		if (growthPlaceDto == null) {
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

		if (growthPlaceDto.getColumnsPositions() < 0 || growthPlaceDto.getRowsPositions() < 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The number of positions rows/columns cannot be negative");
			return false;
		}

		// Check if positions layout is changed. If there are sensors or plants inside
		// the growth place it cannot be updated
		List<PositionByGrowthPlace> listPositionByGrowthPlace = positionByGrowthPlaceDao
				.getPositionsByGrowthPlace(growthPlace.getId());
		int nRows = listPositionByGrowthPlace.stream().mapToInt(p -> p.getRowIndex()).max().getAsInt() + 1;
		int nCols = listPositionByGrowthPlace.stream().mapToInt(p -> p.getColumnIndex()).max().getAsInt() + 1;
		if (growthPlaceDto.getRowsPositions() != nRows || growthPlaceDto.getColumnsPositions() != nCols) {
			if (listPositionByGrowthPlace.stream().anyMatch(p -> p.getIdPlant() != null)) {
				this.setErrorOccurred(true);
				this.setErrorMessage("Cannot change positions layout of the growth place: there are plants inside it");
				return false;
			}

			if (listPositionByGrowthPlace.stream()
					.anyMatch(p -> p.getListSensors() != null && p.getListSensors().size() > 0)) {
				this.setErrorOccurred(true);
				this.setErrorMessage("Cannot change positions layout of the growth place: there are sensors inside it");
				return false;
			}

			// Delete old positions
			for(PositionByGrowthPlace pos : listPositionByGrowthPlace) {
				positionByIdDao.delete(pos.getId());
				positionByGrowthPlaceDao.delete(pos.getGrowthPlaceId(), pos.isFree(), pos.getId());
				positionByPlantDao.delete(pos.getIdPlant(), pos.getId());
				for(UUID idSensor : pos.getListSensors()) {
					positionBySensorDao.delete(idSensor, pos.getId());
				}
			}

			// Create new positions
			try {
				if (growthPlaceDto.getColumnsPositions() > 0 && growthPlaceDto.getRowsPositions() > 0) {
					for (int r = 0; r < growthPlaceDto.getRowsPositions(); r++) {
						for (int c = 0; c < growthPlaceDto.getColumnsPositions(); c++) {
							UUID idPosition = Uuids.timeBased();

							PositionDto positionDto = new PositionDto();
							positionDto.setRowIndex(r);
							positionDto.setColIndex(c);
							positionDto.setFree(true);
							positionDto.setIdGrowthPlace(growthPlace.getId());
							positionDto.setGrowthPlaceName(growthPlace.getName());

							PositionById positionById = positionMapper.toEntity(idPosition, positionDto,
									PositionById.class);
							PositionByGrowthPlace positionByGrowthPlace = positionMapper.toEntity(idPosition,
									positionDto, PositionByGrowthPlace.class);

							positionByIdDao.save(positionById);
							positionByGrowthPlaceDao.save(positionByGrowthPlace);
						}
					}
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				this.setErrorOccurred(true);
				this.setErrorMessage("An error occured during the conversion from entities to dtos");
				return false;
			}
		}

		growthPlace = growthPlaceByIdMapper.toEntity(idGrowthPlace, growthPlaceDto);

		// Check if there is already another growth place with the same name
		GrowthPlaceById sameNameGrowthPlace = growthPlaceByIdDao.getGrowthPlaceByName(growthPlace.getName());
		if (sameNameGrowthPlace != null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A growth place with the same name already exists");
			return false;
		}

		growthPlaceByIdDao.update(growthPlace);

		return true;
	}
}
