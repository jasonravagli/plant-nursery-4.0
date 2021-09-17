package it.unifi.dinfo.swam.plantnursery.nosql.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.PositionDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.position.Position;
import jakarta.enterprise.context.Dependent;

@Dependent
public class PositionMapper {
	
	public PositionDto toDto(Position entity) {
		PositionDto dto = new PositionDto();
		dto.setId(entity.getId());
		dto.setColIndex(entity.getColumnIndex());
		dto.setRowIndex(entity.getRowIndex());
		dto.setFree(entity.isFree());
		dto.setGrowthPlaceName(entity.getGrowthPlaceName());
		dto.setGrowthPlaceId(entity.getGrowthPlaceId());
		dto.setPlantId(entity.getIdPlant());
		dto.setListSensorsId(entity.getListSensors());
		
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
		entity.setGrowthPlaceId(dto.getGrowthPlaceId());
		entity.setGrowthPlaceName(dto.getGrowthPlaceName());
		entity.setIdPlant(dto.getPlantId());
		entity.setListSensors(dto.getListSensorsId());
		
		return type.cast(entity);
	}
}
