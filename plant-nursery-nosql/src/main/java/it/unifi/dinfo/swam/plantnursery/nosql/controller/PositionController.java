package it.unifi.dinfo.swam.plantnursery.nosql.controller;

import java.util.List;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.dao.GrowthPlaceByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.PositionByGrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.PositionByPlantDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.PositionBySensorDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.PositionDto;
import it.unifi.dinfo.swam.plantnursery.nosql.mapper.PositionsMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.model.GrowthPlaceById;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class PositionController extends BaseController{
	@Inject
	private GrowthPlaceByIdDao growthPlaceByIdDao;

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

		return positionMapper.toDto(positionByGrowthPlaceDao.getPositionsByGrowthPlace(growthPlace));
	}

	public List<PositionDto> getPlantFreePositionsByGrowthPlace(UUID idGrowthPlace) {
		this.cleanErrorFields();

		GrowthPlaceById growthPlace = growthPlaceByIdDao.findById(idGrowthPlace);

		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return null;
		}

		return positionMapper.toDto(positionByGrowthPlaceDao.getPlantFreePositionsByGrowthPlace(growthPlace));
	}

//	public boolean updatePosition(Long idGrowthPlace, Long idPosition, PositionDto positionDto) {
//		this.cleanErrorFields();
//
//		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);
//
//		if (growthPlace == null) {
//			this.setErrorOccurred(true);
//			this.setErrorMessage("The growth place does not exists");
//			return false;
//		}
//
//		Position position = positionDao.findById(idPosition);
//
//		if (position == null) {
//			this.setErrorOccurred(true);
//			this.setErrorMessage("The position does not exists");
//			return false;
//		}
//
//		if ((!position.getGrowthPlace().getId().equals(idGrowthPlace))
//				|| !positionDto.getGrowthPlaceId().equals(idGrowthPlace)) {
//			this.setErrorOccurred(true);
//			this.setErrorMessage("Cannot move positions between growth places");
//			return false;
//		}
//
//		if (position.getRowIndex() != positionDto.getRowIndex()
//				|| position.getColumnIndex() != positionDto.getColIndex()) {
//			this.setErrorOccurred(true);
//			this.setErrorMessage("Cannot change the position indexes");
//			return false;
//		}
//
//		try {
//			position = positionMapper.updateEntity(position, positionDto);
//		} catch (IllegalArgumentException e) {
//			this.setErrorOccurred(true);
//			this.setErrorMessage(e.getMessage());
//			return false;
//		}
//		positionDao.update(position);
//
//		return true;
//	}
}
