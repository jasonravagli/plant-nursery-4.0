package it.unifi.dinfo.swam.plantnursery.nosql.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.PlantDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.plant.Plant;
import jakarta.enterprise.context.Dependent;

@Dependent
public class PlantMapper {
	
	public PlantDto toDto(Plant entity) {
		PlantDto dto = new PlantDto();
		dto.setId(entity.getId());
		dto.setGrowthPlaceId(entity.getIdGrowthPlace());
		dto.setPlantingDate(entity.getPlantingDate());
		dto.setSaleDate(entity.getSaleDate());
		dto.setSold(entity.isSold());
		dto.setSpeciesId(entity.getSpeciesId());
		dto.setSpeciesName(entity.getSpeciesName());
		return dto;
	}
	
	public <T extends Plant> List<PlantDto> toDto(List<T> entities){
		return entities.stream().map(this::toDto).collect(Collectors.toList());
	}

	public <T extends Plant> T toEntity(UUID id, PlantDto dto, Class<T> type) throws InstantiationException, IllegalAccessException {
		Plant entity = type.newInstance();
		entity.setId(id);
		entity.setIdGrowthPlace(dto.getGrowthPlaceId());
		entity.setPlantingDate(dto.getPlantingDate());
		entity.setSaleDate(dto.getSaleDate());
		entity.setSold(dto.isSold());
		entity.setSpeciesId(dto.getSpeciesId());
		entity.setSpeciesName(dto.getSpeciesName());
		
		return type.cast(entity);
	}
}
