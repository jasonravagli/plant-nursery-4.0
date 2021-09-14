package it.unifi.dinfo.swam.plantnursery.nosql.mapper;

import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.MeasuramentDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.Measurament;

public class MeasuramentsMapper {
	
	public MeasuramentDto toDto(Measurament entity) {
		MeasuramentDto dto = new MeasuramentDto();
		dto.setId(entity.getId());
		dto.setIdGrowthPlace(entity.getIdGrowthPlace());
		dto.setIdPlant(entity.getIdPlant());
		dto.setIdPosition(entity.getIdPosition());
		dto.setIdSensor(entity.getIdSensor());
		dto.setMeasuramentDate(entity.getMeasDate());
		dto.setType(entity.getType());
		dto.setUnit(entity.getUnit());
		dto.setValue(entity.getValue());
		
		return dto;
	}

	public <T extends Measurament> T toEntity(UUID id, MeasuramentDto dto, Class<T> type) throws InstantiationException, IllegalAccessException {
		Measurament entity = type.newInstance();
		entity.setId(id);
		entity.setIdGrowthPlace(dto.getIdGrowthPlace());
		entity.setIdPlant(dto.getIdPlant());
		entity.setIdPosition(dto.getIdPosition());
		entity.setIdSensor(dto.getIdSensor());
		entity.setMeasDate(dto.getMeasuramentDate());
		entity.setType(dto.getType());
		entity.setUnit(dto.getUnit());
		entity.setValue(dto.getValue());
		
		return type.cast(entity);
	}
}
