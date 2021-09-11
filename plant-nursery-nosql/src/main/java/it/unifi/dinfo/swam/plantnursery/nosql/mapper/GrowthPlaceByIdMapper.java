package it.unifi.dinfo.swam.plantnursery.nosql.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.GrowthPlaceDto;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.GrowthPlaceType;
import it.unifi.dinfo.swam.plantnursery.nosql.model.GrowthPlaceById;
import jakarta.enterprise.context.Dependent;

@Dependent
public class GrowthPlaceByIdMapper {
	
	public GrowthPlaceDto toDto(GrowthPlaceById entity) {
		GrowthPlaceDto dto = new GrowthPlaceDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setType(GrowthPlaceType.valueOf(entity.getType()));
		dto.setLatitude(entity.getLatitude());
		dto.setLongitude(entity.getLongitude());
		dto.setRowsPositions(entity.getRowPositions());
		dto.setColumnsPositions(entity.getRowPositions());
		
		return dto;
	}
	
	public List<GrowthPlaceDto> toDto(List<GrowthPlaceById> entities){
		return entities.stream().map(this::toDto).collect(Collectors.toList());
	}
	
	public GrowthPlaceById toEntity(UUID id, GrowthPlaceDto dto) {
		GrowthPlaceById entity =  new GrowthPlaceById();
		entity.setId(id);
		entity.setName(dto.getName());
		entity.setType(dto.getType().toString());
		entity.setLatitude(dto.getLatitude());
		entity.setLongitude(dto.getLongitude());
		entity.setRowPositions(dto.getRowsPositions());
		entity.setColPositions(dto.getColumnsPositions());
		
		return entity;
	}

}
