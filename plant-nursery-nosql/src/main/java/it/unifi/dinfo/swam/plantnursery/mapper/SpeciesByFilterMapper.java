package it.unifi.dinfo.swam.plantnursery.mapper;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.SpeciesDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesByFilter;
import jakarta.enterprise.context.Dependent;

@Dependent
public class SpeciesByFilterMapper {
	
	public SpeciesByFilter toEntity(SpeciesDto dto) {
		SpeciesByFilter entity = ModelFactory.speciesByFilter();
		entity.setName(dto.getName());
		entity.setType(dto.getType().toString());

		return entity;
	}
}
