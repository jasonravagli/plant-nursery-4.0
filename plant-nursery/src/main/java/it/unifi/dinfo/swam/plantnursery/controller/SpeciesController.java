package it.unifi.dinfo.swam.plantnursery.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.PlantDao;
import it.unifi.dinfo.swam.plantnursery.dao.SpeciesDao;
import it.unifi.dinfo.swam.plantnursery.dto.SpeciesDto;
import it.unifi.dinfo.swam.plantnursery.mapper.SpeciesMapper;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.PlantType;
import it.unifi.dinfo.swam.plantnursery.model.Species;

@RequestScoped
public class SpeciesController extends BaseController {

	@Inject
	private SpeciesDao speciesDao;

	@Inject
	private PlantDao plantDao;
	
	@Inject
	private SpeciesMapper speciesMapper;

	public boolean deleteSpecies(Long idSpecies) {
		this.cleanErrorFields();
		
		Species species = speciesDao.findById(idSpecies);

		if (species == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species does not exists");
			return false;
		}

		// If there are plants with this species it cannot be deleted
		List<Plant> plants = plantDao.getPlantsBySpecies(species);
		if (plants.size() > 0) {
			this.setErrorOccurred(true);
			this.setErrorMessage("Cannot delete the species: there are plants belonging to it");
			return false;
		}

		speciesDao.delete(species);
		return true;
	}


	public List<SpeciesDto> getFilteredSpecies(PlantType plantType, String prefixName) {
		this.cleanErrorFields();
		
		return speciesMapper.toDto(speciesDao.getFilteredSpecies(plantType, prefixName));
	}

	public Species getSpeciesById(Long idSpecies) {
		this.cleanErrorFields();
		
		return speciesDao.findById(idSpecies);
	}

	public boolean saveSpecies(Species species) {
		this.cleanErrorFields();
		
		if (species == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species cannot be null");
			return false;
		}
		
		// Check if there is already a species with the same name
		if (speciesDao.getSpeciesByName(species.getName()) != null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A species with the same name already exists");
			return false;
		}
		
		Species newSpecies = ModelFactory.species();
		newSpecies.setName(species.getName());
		newSpecies.setDescription(species.getDescription());
		newSpecies.setType(species.getType());
		newSpecies.setGrowthPlaceTypes(species.getGrowthPlaceTypes());
		newSpecies.setLifeParams(species.getLifeParams());

		speciesDao.save(newSpecies);
		return true;
	}

	public boolean updateSpecies(Long idSpecies, Species species) {
		this.cleanErrorFields();
		
		if (species == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species cannot be null");
			return false;
		}
		
		Species speciesToUpdate = speciesDao.findById(idSpecies); 
		
		if (speciesToUpdate == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species does not exists");
			return false;
		}

		// Check if there is already another species with the same name
		Species sameNameSpecies = speciesDao.getSpeciesByName(species.getName());
		if (sameNameSpecies != null && !sameNameSpecies.equals(speciesToUpdate)) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A species with the same name already exists");
			return false;
		}
		
		speciesToUpdate.setDescription(species.getDescription());
		speciesToUpdate.setName(species.getName());
		speciesToUpdate.setType(species.getType());
		speciesToUpdate.setGrowthPlaceTypes(species.getGrowthPlaceTypes());
		speciesToUpdate.setLifeParams(species.getLifeParams());

		speciesDao.update(speciesToUpdate);
		return true;
	}
}
