package it.unifi.dinfo.swam.plantnursery.mapper;

import java.util.HashSet;
import java.util.Set;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.GrowthPlaceType;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.LifeParameterDto;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.SpeciesDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesById;
import jakarta.enterprise.context.Dependent;

@Dependent
public class SpeciesByIdMapper {
	
	public SpeciesById toEntity(SpeciesDto dto) {
		SpeciesById entity = ModelFactory.speciesById();
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setType(dto.getType().toString());
		
		Set<String> growthPlaceTypes = new HashSet<String>();
		for(GrowthPlaceType type : dto.getGrowthPlaceTypes()) {
			growthPlaceTypes.add(type.toString());
		}
		entity.setGrowthPlaceTypes(growthPlaceTypes);
		
		Set<String> stringLifeParams = new HashSet<String>();
		for(LifeParameterDto lifeParam : dto.getLifeParams()) {
			stringLifeParams.add(lifeParam.toString());
		}
		entity.setLifeParams(stringLifeParams);
		
		return entity;
	}

}
