package it.unifi.dinfo.swam.plantnursery.nosql.controller;

import java.util.List;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.GrowthPlaceByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionByPlantDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.position.PositionBySensorDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.PositionDto;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.PositionsMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.model.GrowthPlaceById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionByGrowthPlace;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionById;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionByPlant;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.PositionBySensor;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class PositionController extends BaseController {
	
	@Inject
	private GrowthPlaceByIdDao growthPlaceByIdDao;

	@Inject
	private PositionByIdDao positionByIdDao;
	
	@Inject
	private PositionByPlantDao positionByPlantDao;

	@Inject
	private PositionByGrowthPlaceDao positionByGrowthPlaceDao;

	@Inject
	private PositionBySensorDao positionBySensorDao;

	@Inject
	private PositionsMapper positionMapper;

	public List<PositionDto> getPositionsByGrowthPlace(UUID idGrowthPlace) {
		this.cleanErrorFields();

		GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(idGrowthPlace);

		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return null;
		}

		return positionMapper.toDto(positionByGrowthPlaceDao.getPositionsByGrowthPlace(idGrowthPlace));
	}

	public List<PositionDto> getPlantFreePositionsByGrowthPlace(UUID idGrowthPlace) {
		this.cleanErrorFields();

		GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(idGrowthPlace);

		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return null;
		}

		return positionMapper.toDto(positionByGrowthPlaceDao.getPlantFreePositionsByGrowthPlace(idGrowthPlace));
	}

	public boolean updatePosition(UUID idGrowthPlace, UUID idPosition, PositionDto positionDto) {
		this.cleanErrorFields();

		GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(idGrowthPlace);

		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return false;
		}

		PositionById positionToUpdate = positionByIdDao.findById(idPosition);

		if (positionToUpdate == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The position does not exists");
			return false;
		}

		if ((!positionToUpdate.getGrowthPlaceId().equals(idGrowthPlace))
				|| !positionDto.getIdGrowthPlace().equals(idGrowthPlace)) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Cannot move positions between growth places");
			return false;
		}

		if (positionToUpdate.getRowIndex() != positionDto.getRowIndex()
				|| positionToUpdate.getColumnIndex() != positionDto.getColIndex()) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Cannot change the position indexes");
			return false;
		}

		try {
			PositionById positionById = positionMapper.toEntity(idPosition, positionDto, PositionById.class);
			PositionByGrowthPlace positionByGrowthPlace = positionMapper.toEntity(idPosition, positionDto, PositionByGrowthPlace.class);
			PositionByPlant positionByPlant = positionMapper.toEntity(idPosition, positionDto, PositionByPlant.class);
			PositionBySensor positionBySensor = positionMapper.toEntity(idPosition, positionDto, PositionBySensor.class);
			
			positionByIdDao.update(positionById);
			positionByGrowthPlaceDao.update(positionToUpdate, positionByGrowthPlace);
			positionByPlantDao.update(positionToUpdate, positionByPlant);
			positionBySensorDao.update();
			
			// AGGIORNARE ANCHE TABELLE DEI SENSORI E DELLE PIANTE
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			this.setErrorOccurred(true);
			this.setErrorMessage("An error occured during the conversion from entities to dtos");
			return false;
		}

		return true;
	}
}
