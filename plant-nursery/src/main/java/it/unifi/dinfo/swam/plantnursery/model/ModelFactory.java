package it.unifi.dinfo.swam.plantnursery.model;

import java.util.UUID;

public class ModelFactory {
	
	private ModelFactory() {
	}
	
	public static User user() {
		return new User(UUID.randomUUID().toString());
	}
	
	public static GrowthPlace growthPlace() {
		return new GrowthPlace(UUID.randomUUID().toString());
	}
	
	public static Measurement measurement() {
		return new Measurement(UUID.randomUUID().toString());
	}
	
	public static Plant plant() {
		return new Plant(UUID.randomUUID().toString());
	}
	
	public static Position position() {
		return new Position(UUID.randomUUID().toString());
	}
	
	public static Sensor sensor() {
		return new Sensor(UUID.randomUUID().toString());
	}
	
	public static Species species() {
		return new Species(UUID.randomUUID().toString());
	}
}
