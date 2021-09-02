package it.unifi.dinfo.swam.plantnursery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "growth_places")
public class GrowthPlace extends BaseEntity {
	
	@NotNull
	@Column(unique = true)
	private String name;
	private float latitude;
	private float longitude;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private GrowthPlaceType type;
	
	protected GrowthPlace() {
	}
	
	public GrowthPlace(String uuid) {
		super(uuid);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public GrowthPlaceType getType() {
		return type;
	}

	public void setType(GrowthPlaceType type) {
		this.type = type;
	}
}
