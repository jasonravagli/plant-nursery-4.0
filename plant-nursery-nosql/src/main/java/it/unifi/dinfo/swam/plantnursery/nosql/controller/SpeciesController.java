package it.unifi.dinfo.swam.plantnursery.nosql.controller;

import java.util.List;
import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.mapper.SpeciesByFilterMapper;
import it.unifi.dinfo.swam.plantnursery.mapper.SpeciesByIdMapper;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.SpeciesByFilterDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dao.SpeciesByIdDao;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.PlantType;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.SpeciesBaseInfoDto;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.SpeciesDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesByFilter;
import it.unifi.dinfo.swam.plantnursery.nosql.model.SpeciesById;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class SpeciesController extends BaseController {

	@Inject
	private SpeciesByIdDao speciesByIdDao;
	
	@Inject
	private SpeciesByFilterDao speciesByFilterDao;
	
	@Inject
	private SpeciesByIdMapper speciesByIdMapper;
	
	@Inject
	private SpeciesByFilterMapper speciesByFilterMapper;

	public boolean deleteSpecies(UUID idSpecies) {

		return false;
	}

	public List<SpeciesBaseInfoDto> getFilteredSpecies(PlantType plantType, String prefixName) {
		return null;
	}

	public SpeciesDto getSpeciesById(UUID idSpecies) {
		return null;
	}

	public boolean saveSpecies(SpeciesDto speciesDto) {
		this.cleanErrorFields();

		if (speciesDto == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species cannot be null");
			return false;
		}

		// Check if there is already a species with the same name
		if (speciesByFilterDao.getSpeciesByName(speciesDto.getName()) != null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A species with the same name already exists");
			return false;
		}
		
		SpeciesById newSpeciesById = speciesByIdMapper.toEntity(speciesDto);
		speciesByIdDao.save(newSpeciesById);
		
		SpeciesByFilter newSpeciesByFilter = speciesByFilterMapper.toEntity(speciesDto);
		speciesByFilterDao.save(newSpeciesByFilter);

		return true;
	}

	public boolean updateSpecies(UUID idSpecies, SpeciesDto species) {
		return true;
	}
}
