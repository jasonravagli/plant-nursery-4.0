package it.unifi.dinfo.swam.plantnursery.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.GrowthPlaceType;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.LifeParameterDto;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.PlantType;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.SpeciesDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesById;
import jakarta.enterprise.context.Dependent;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

@Dependent
public class SpeciesByIdMapper {

	public SpeciesDto toDto(SpeciesById entity) {
		SpeciesDto dto = new SpeciesDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setType(PlantType.valueOf(entity.getType()));

		Set<GrowthPlaceType> growthPlaceTypes = entity.getGrowthPlaceTypes().stream()
				.map(s -> GrowthPlaceType.valueOf(s)).collect(Collectors.toSet());
		dto.setGrowthPlaceTypes(growthPlaceTypes);

		Jsonb jsonb = JsonbBuilder.create();
		Set<LifeParameterDto> lifeParams = entity.getLifeParams().stream()
				.map(s -> jsonb.fromJson(s, LifeParameterDto.class)).collect(Collectors.toSet());
		dto.setLifeParams(lifeParams);
		
		return dto;
	}

	public SpeciesById toEntity(UUID id, SpeciesDto dto) {
		SpeciesById entity = new SpeciesById();
		entity.setId(id);
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setType(dto.getType().toString());

		Set<String> growthPlaceTypes = new HashSet<String>();
		for (GrowthPlaceType type : dto.getGrowthPlaceTypes()) {
			growthPlaceTypes.add(type.toString());
		}
		entity.setGrowthPlaceTypes(growthPlaceTypes);

		Set<String> stringLifeParams = new HashSet<String>();
		for (LifeParameterDto lifeParam : dto.getLifeParams()) {
			stringLifeParams.add(lifeParam.toString());
		}
		entity.setLifeParams(stringLifeParams);

		return entity;
	}

}
