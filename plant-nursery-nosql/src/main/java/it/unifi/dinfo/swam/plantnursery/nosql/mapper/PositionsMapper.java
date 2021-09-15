package it.unifi.dinfo.swam.plantnursery.nosql.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.PositionDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.Position;
import jakarta.enterprise.context.Dependent;

@Dependent
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
		dto.setIdSensors(entity.getIdSensors());
		
		return dto;
	}
	
	public <T extends Position> List<PositionDto> toDto(List<T> entities){
		return entities.stream().map(this::toDto).collect(Collectors.toList());
	}

	public <T extends Position> T toEntity(UUID id, PositionDto dto, Class<T> type) throws InstantiationException, IllegalAccessException {
		Position entity = type.newInstance();
		entity.setId(id);
		entity.setColumnIndex(dto.getColIndex());
		entity.setRowIndex(dto.getRowIndex());	
		entity.setFree(dto.isFree());
		entity.setGrowthPlaceId(dto.getIdGrowthPlace());
		entity.setGrowthPlaceName(dto.getGrowthPlaceName());
		entity.setIdPlant(dto.getIdPlant());
		entity.setIdSensors(dto.getIdSensors());
		
		return type.cast(entity);
	}
}
