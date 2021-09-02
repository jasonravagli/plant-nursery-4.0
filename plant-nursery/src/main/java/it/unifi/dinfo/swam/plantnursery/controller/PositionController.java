package it.unifi.dinfo.swam.plantnursery.controller;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.GrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.dao.PositionDao;
import it.unifi.dinfo.swam.plantnursery.dto.PositionDto;
import it.unifi.dinfo.swam.plantnursery.mapper.PositionMapper;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.Position;

@RequestScoped
public class PositionController extends BaseController {

	@Inject
	private GrowthPlaceDao growthPlaceDao;

	@Inject
	private PositionDao positionDao;

	@Inject
	private PositionMapper positionMapper;

	public List<PositionDto> getPositionsByGrowthPlace(Long idGrowthPlace) {
		this.cleanErrorFields();

		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);

		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return null;
		}

		return positionMapper.toDto(positionDao.getPositionsByGrowthPlace(growthPlace));
	}

	public List<PositionDto> getPlantFreePositionsByGrowthPlace(Long idGrowthPlace) {
		this.cleanErrorFields();

		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);

		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return null;
		}

		return positionMapper.toDto(positionDao.getPlantFreePositionsByGrowthPlace(growthPlace));
	}

	public boolean updatePosition(Long idGrowthPlace, Long idPosition, PositionDto positionDto) {
		this.cleanErrorFields();

		GrowthPlace growthPlace = growthPlaceDao.findById(idGrowthPlace);

		if (growthPlace == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The growth place does not exists");
			return false;
		}

		Position position = positionDao.findById(idPosition);

		if (position == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The position does not exists");
			return false;
		}

		if ((!position.getGrowthPlace().getId().equals(idGrowthPlace))
				|| !positionDto.getGrowthPlaceId().equals(idGrowthPlace)) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Cannot move positions between growth places");
			return false;
		}

		if (position.getRowIndex() != positionDto.getRowIndex()
				|| position.getColumnIndex() != positionDto.getColIndex()) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Cannot change the position indexes");
			return false;
		}

		try {
			position = positionMapper.updateEntity(position, positionDto);
		} catch (IllegalArgumentException e) {
			this.setErrorOccurred(true);
			this.setErrorMessage(e.getMessage());
			return false;
		}
		positionDao.update(position);

		return true;
	}
}
