package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.util.UUID;

import jakarta.json.bind.JsonbBuilder;
import jakarta.nosql.mapping.Id;
import jakarta.nosql.mapping.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
	
	@Id("id")
	private UUID id;

	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof BaseEntity)) {
			return false;
		}
		
		BaseEntity other = (BaseEntity) obj;
		
		return id.equals(other.getId());
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id.hashCode();
        return result;
    }
	
	@Override
    public String toString(){
        try {
        	return JsonbBuilder.create().toJson(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
