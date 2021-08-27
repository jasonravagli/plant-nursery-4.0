package it.unifi.dinfo.swam.plantnursery.mapper;

import javax.enterprise.context.Dependent;
import javax.ws.rs.NotSupportedException;

import it.unifi.dinfo.swam.plantnursery.dto.SpeciesDto;
import it.unifi.dinfo.swam.plantnursery.model.Species;

@Dependent
public class SpeciesMapper extends BaseMapper<Species, SpeciesDto> {

	@Override
	public SpeciesDto toDto(Species obj) throws IllegalArgumentException {
		SpeciesDto dto = new SpeciesDto();
		dto.setId(obj.getId());
		dto.setName(obj.getName());
		dto.setPlantType(obj.getType().toString());
		
		return dto;
	}

	@Override
	public Species toEntity(SpeciesDto dto) throws IllegalArgumentException {
		throw new NotSupportedException();
	}

	@Override
	public Species updateEntity(Species obj, SpeciesDto dto) throws IllegalArgumentException {
		throw new NotSupportedException();
	}

}
