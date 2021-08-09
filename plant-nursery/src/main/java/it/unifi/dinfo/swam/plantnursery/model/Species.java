package it.unifi.dinfo.swam.plantnursery.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "species")
public class Species extends BaseEntity {

	private String name;
	private String description;

	@Enumerated(EnumType.STRING)
	private PlantType type;

	@ElementCollection
	@CollectionTable(name = "species_growth_places", joinColumns = @JoinColumn(name = "species_id"))
	@Enumerated(EnumType.STRING)
	private Set<GrowthPlaceType> growthPlaceTypes;

	@ElementCollection
	@CollectionTable(name = "life_parameters", joinColumns = @JoinColumn(name = "species_id"))
	private List<LifeParameter> lifeParams;
	
	protected Species() {
	}
	
	public Species(String uuid) {
		super(uuid);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PlantType getType() {
		return type;
	}

	public void setType(PlantType type) {
		this.type = type;
	}

	public List<LifeParameter> getLifeParams() {
		return lifeParams;
	}

	public void setLifeParams(List<LifeParameter> lifeParams) {
		this.lifeParams = lifeParams;
	}
	
	public void addLifeParam(LifeParameter param){
		this.lifeParams.add(param);
	}
	
	public void removeLifeParam(LifeParameter param){
		this.lifeParams.remove(param);
	}

	public Set<GrowthPlaceType> getGrowthPlaceTypes() {
		return growthPlaceTypes;
	}

	public void setGrowthPlaceTypes(Set<GrowthPlaceType> growthPlaceTypes) {
		this.growthPlaceTypes = growthPlaceTypes;
	}
	
	public void addGrowthPlaceType(GrowthPlaceType type) {
		this.growthPlaceTypes.add(type);
	}
	
	public void removeGrowthPlaceType(GrowthPlaceType type){
		this.growthPlaceTypes.remove(type);
	}

}
