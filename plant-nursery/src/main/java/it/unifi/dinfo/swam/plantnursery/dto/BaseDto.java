package it.unifi.dinfo.swam.plantnursery.dto;

import javax.json.bind.JsonbBuilder;

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
