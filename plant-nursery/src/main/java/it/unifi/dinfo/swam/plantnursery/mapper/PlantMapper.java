package it.unifi.dinfo.swam.plantnursery.mapper;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import it.unifi.dinfo.swam.plantnursery.dao.SpeciesDao;
import it.unifi.dinfo.swam.plantnursery.dto.PlantDto;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Species;

@Dependent
public class PlantMapper extends BaseMapper<Plant, PlantDto>{

	@Inject
	private SpeciesDao speciesDao;
	
	@Override
	public PlantDto toDto(Plant obj) throws IllegalArgumentException {
		if(obj == null) {
			throw new IllegalArgumentException("The object to convert cannot be null");
		}
		
		PlantDto dto = new PlantDto();
		dto.setId(obj.getId());
		dto.setSaleDate(obj.getSaleDate());
		dto.setSold(obj.isSold());
		dto.setSpeciesId(obj.getSpecies().getId());
		dto.setSpeciesName(obj.getSpecies().getName());
		
		return dto;
	}

	@Override
	public Plant toEntity(PlantDto dto) throws IllegalArgumentException {
		if(dto == null) {
			throw new IllegalArgumentException("The dto to convert cannot be null");
		}
		
		Plant obj = ModelFactory.plant();
		obj.setSaleDate(dto.getSaleDate());
		obj.setSold(dto.isSold());
		
		Species species = speciesDao.findById(dto.getSpeciesId());
		if(species == null){
			throw new IllegalArgumentException("The plant species does not exists");
		}
		obj.setSpecies(species);
		obj.setPlantingDate(dto.getPlantingDate());
		
		return obj;
	}

	@Override
	public Plant updateEntity(Plant obj, PlantDto dto) throws IllegalArgumentException {
		if (obj == null) {
			throw new IllegalArgumentException("The object to update cannot be null");
		}
		
		if (dto == null) {
			throw new IllegalArgumentException("The dto cannot be null");
		}
		
		obj.setPlantingDate(dto.getPlantingDate());
		obj.setSaleDate(dto.getSaleDate());
		obj.setSold(dto.isSold());
		
		Species species = speciesDao.findById(dto.getSpeciesId());
		if(species == null){
			throw new IllegalArgumentException("The plant species does not exists");
		}
		obj.setSpecies(species);
		
		return obj;
	}
}
