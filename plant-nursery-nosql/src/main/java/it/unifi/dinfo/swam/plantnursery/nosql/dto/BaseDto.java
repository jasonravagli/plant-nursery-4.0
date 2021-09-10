package it.unifi.dinfo.swam.plantnursery.nosql.dto;

import jakarta.json.bind.JsonbBuilder;

public abstract class BaseDto {
	
	@Override
    public String toString(){
        try {
        	return JsonbBuilder.create().toJson(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
