package it.unifi.dinfo.swam.plantnursery.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.unifi.dinfo.swam.plantnursery.dao.PlantDao;
import it.unifi.dinfo.swam.plantnursery.dao.SpeciesDao;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.PlantType;
import it.unifi.dinfo.swam.plantnursery.model.Species;

@RequestScoped
public class SpeciesController extends BaseController {

	@Inject
	private SpeciesDao speciesDao;

	@Inject
	private PlantDao plantDao;

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


	public List<Species> getFilteredSpecies(PlantType plantType, String prefixName) {
		this.cleanErrorFields();
		
		return speciesDao.getFilteredSpecies(plantType, prefixName);
	}

	public Species getSpeciesById(Long idSpecies) {
		this.cleanErrorFields();
		
		return speciesDao.findById(idSpecies);
	}

//	public List<Species> getSpecies() {
//		return speciesDao.getSpecies();
//	}
//	
//	public Species getSpeciesByName(String name) {
//		return speciesDao.getSpeciesByName(name);
//	}
//
//	public List<Species> getSpeciesByPlantType(PlantType type) {
//		return speciesDao.getSpeciesByPlantType(type);
//	}
//
//	public List<Species> getSpeciesStartingByName(String prefixName) {
//		return speciesDao.getSpeciesStartingByName(prefixName);
//	}

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

		speciesDao.save(species);
		return true;
	}

	public boolean updateSpecies(Species species) {
		this.cleanErrorFields();
		
		if (species == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species cannot be null");
			return false;
		}
		
		if (speciesDao.findById(species.getId()) == null) {
			this.setErrorOccurred(true);
			this.setErrorMessage("The species does not exists");
			return false;
		}

		// Check if there is already another species with the same name
		Species sameNameSpecies = speciesDao.getSpeciesByName(species.getName());
		if (sameNameSpecies != null && !sameNameSpecies.equals(species)) {
			this.setErrorOccurred(true);
			this.setErrorMessage("A species with the same name already exists");
			return false;
		}

		speciesDao.update(species);
		return true;
	}
}
