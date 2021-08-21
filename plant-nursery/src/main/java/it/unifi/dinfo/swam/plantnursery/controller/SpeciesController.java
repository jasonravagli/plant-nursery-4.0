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
public class SpeciesController {

	@Inject
	private SpeciesDao speciesDao;

	@Inject
	private PlantDao plantDao;

	public boolean deleteSpecies(Long idSpecies) {
		Species species = speciesDao.findById(idSpecies);

		if (species == null)
			return false;

		// If there are plants with this species it cannot be deleted
		List<Plant> plants = plantDao.getPlantsBySpecies(species);
		if (plants.size() > 0)
			return false;

		speciesDao.delete(species);
		return true;
	}

	public List<Species> getSpecies() {
		return speciesDao.getSpecies();
	}

	public Species getSpeciesById(Long idSpecies) {
		return speciesDao.findById(idSpecies);
	}

	public Species getSpeciesByName(String name) {
		return speciesDao.getSpeciesByName(name);
	}

	public List<Species> getSpeciesByPlantType(PlantType type) {
		return speciesDao.getSpeciesByPlantType(type);
	}

	public List<Species> getSpeciesStartingByName(String prefixName) {
		return speciesDao.getSpeciesStartingByName(prefixName);
	}

	public boolean saveSpecies(Species species) {
		// Check if there is already a species with the same name
		if (speciesDao.getSpeciesByName(species.getName()) != null)
			return false;

		speciesDao.save(species);
		return true;
	}

	public boolean updateSpecies(Species species) {
		if (speciesDao.findById(species.getId()) == null)
			return false;

		// Check if there is already another species with the same name
		Species sameNameSpecies = speciesDao.getSpeciesByName(species.getName());
		if (sameNameSpecies != null && !sameNameSpecies.equals(species))
			return false;

		speciesDao.update(species);
		return true;
	}
}
