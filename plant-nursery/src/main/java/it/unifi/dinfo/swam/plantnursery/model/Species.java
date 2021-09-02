package it.unifi.dinfo.swam.plantnursery.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "species")
public class Species extends BaseEntity {

	@NotNull
	@Column(unique = true)
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
	private Set<LifeParameter> lifeParams;
	
	protected Species() {
		growthPlaceTypes = new HashSet<>();
		lifeParams = new HashSet<>();
	}
	
	public Species(String uuid) {
		super(uuid);
		
		growthPlaceTypes = new HashSet<>();
		lifeParams = new HashSet<>();
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

	public Set<LifeParameter> getLifeParams() {
		return lifeParams;
	}

	public void setLifeParams(Set<LifeParameter> lifeParams) {
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
