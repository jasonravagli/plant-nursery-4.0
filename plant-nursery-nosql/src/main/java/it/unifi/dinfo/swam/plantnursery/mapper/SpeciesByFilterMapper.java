package it.unifi.dinfo.swam.plantnursery.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.PlantType;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.SpeciesBaseInfoDto;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.SpeciesDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesByFilter;
import jakarta.enterprise.context.Dependent;

@Dependent
public class SpeciesByFilterMapper {
	
	public SpeciesBaseInfoDto toDto(SpeciesByFilter entity) {
		SpeciesBaseInfoDto dto = new SpeciesBaseInfoDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setPlantType(PlantType.valueOf(entity.getType()));
		
		return dto;
	}
	
	public List<SpeciesBaseInfoDto> toDto(List<SpeciesByFilter> entities){
		return entities.stream().map(this::toDto).collect(Collectors.toList());
	}
	
	public SpeciesByFilter toEntity(UUID id, SpeciesDto dto) {
		SpeciesByFilter entity = new SpeciesByFilter();
		entity.setId(id);
		entity.setName(dto.getName());
		entity.setType(dto.getType().toString());

		return entity;
	}
}
