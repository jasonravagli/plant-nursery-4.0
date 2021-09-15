package it.unifi.dinfo.swam.plantnursery.nosql.mapper;

import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.PositionDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.Position;

public class PositionsMapper {

	public PositionDto toDto(Position entity) {
		PositionDto dto = new PositionDto();
		dto.setId(entity.getId());
		dto.setColIndex(entity.getColumnIndex());
		dto.setRowIndex(entity.getRowIndex());
		dto.setFree(entity.isFree());
		dto.setGrowthPlaceName(entity.getGrowthPlaceName());
		dto.setIdGrowthPlace(entity.getGrowthPlaceId());
		dto.setIdPlant(entity.getIdPlant());
		
		//Set<UUID> sensorsIds = entity.getIdSensors().stream().map(t -> UUID.valueOf(t)).collect(Collectors.toSet());
		dto.setIdSensors(entity.getIdSensors());
		
		
		
		return dto;
	}

	public <T extends Position> T toEntity(UUID id, PositionDto dto, Class<T> type) throws InstantiationException, IllegalAccessException {
		Position entity = type.newInstance();
		entity.setId(id);
		entity.setColumnIndex(dto.getColIndex());
		entity.setRowIndex(dto.getRowIndex());
		entity.setFree(dto.isFree());
		entity.setGrowthPlaceName(dto.getGrowthPlaceName());
		entity.setGrowthPlaceId(dto.getIdGrowthPlace());
		entity.setIdPlant(dto.getIdPlant());
		
		//Set<UUID> sensorsIds = dto.getIdSensors().stream().map(fp -> fp.toString()).collect(Collectors.toSet());
		entity.setIdSensors(dto.getIdSensors());
		
		return type.cast(entity);
	}
}
