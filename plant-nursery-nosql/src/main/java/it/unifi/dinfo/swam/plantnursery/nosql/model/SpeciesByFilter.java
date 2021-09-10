package it.unifi.dinfo.swam.plantnursery.nosql.model;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

@Entity("species_by_filter")
public class SpeciesByFilter extends BaseEntity {

	@Column("name")
	private String name;

	@Id("type")
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
