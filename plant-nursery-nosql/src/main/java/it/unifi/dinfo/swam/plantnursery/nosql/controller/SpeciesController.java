package it.unifi.dinfo.swam.plantnursery.nosql.controller;

import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;

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
		this.cleanErrorFields();
		
		SpeciesById species = speciesByIdDao.findById(idSpecies);

		if (species == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species does not exists");
			return false;
		}

//		// If there are plants with this species it cannot be deleted
//		List<Plant> plants = plantDao.getPlantsBySpecies(species);
//		if (plants.size() > 0) {
//			this.setErrorOccurred(true);
//			this.setErrorMessage("Cannot delete the species: there are plants belonging to it");
//			return false;
//		}
//
//		speciesDao.delete(species);
		return true;
	}

	public List<SpeciesBaseInfoDto> getFilteredSpecies(PlantType plantType, String prefixName) {
		this.cleanErrorFields();

		List<SpeciesByFilter> listSpecies = speciesByFilterDao
				.getFilteredSpecies(plantType != null ? plantType.toString() : null, prefixName);

		return speciesByFilterMapper.toDto(listSpecies);
	}

	public SpeciesDto getSpeciesById(UUID idSpecies) {
		this.cleanErrorFields();

		SpeciesById species = speciesByIdDao.findById(idSpecies);
		return speciesByIdMapper.toDto(species);
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
		
		UUID id = Uuids.timeBased();
		SpeciesById newSpeciesById = speciesByIdMapper.toEntity(id, speciesDto);
		speciesByIdDao.save(newSpeciesById);

		SpeciesByFilter newSpeciesByFilter = speciesByFilterMapper.toEntity(id, speciesDto);
		speciesByFilterDao.save(newSpeciesByFilter);

		return true;
	}

	public boolean updateSpecies(UUID idSpecies, SpeciesDto speciesDto) {
		this.cleanErrorFields();
		
		if (speciesDto == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species cannot be null");
			return false;
		}
		
		SpeciesById speciesToUpdate = speciesByIdDao.findById(idSpecies); 
		
		if (speciesToUpdate == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species does not exists");
			return false;
		}

		// Check if there is already another species with the same name
		SpeciesByFilter sameNameSpecies = speciesByFilterDao.getSpeciesByName(speciesDto.getName());
		if (sameNameSpecies != null && !sameNameSpecies.equals(speciesToUpdate)) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A species with the same name already exists");
			return false;
		}
		
		SpeciesById updatedSpeciesById = speciesByIdMapper.toEntity(idSpecies, speciesDto);
		speciesByIdDao.update(updatedSpeciesById);
		
		SpeciesByFilter updatedSpeciesByFilter = speciesByFilterMapper.toEntity(idSpecies, speciesDto);
		speciesByFilterDao.update(updatedSpeciesByFilter);
		
		return true;
	}
}
