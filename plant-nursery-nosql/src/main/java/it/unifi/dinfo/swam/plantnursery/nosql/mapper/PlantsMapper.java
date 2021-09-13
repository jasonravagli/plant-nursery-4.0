package it.unifi.dinfo.swam.plantnursery.nosql.mapper;

import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.PlantDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.PlantById;

public class PlantsMapper {
	
	public PlantDto toDto(PlantById entity) {
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

	public <T extends PlantById> T toEntity(UUID id, PlantDto dto, Class<T> type) throws InstantiationException, IllegalAccessException {
		PlantById entity = type.newInstance();
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
