package it.unifi.dinfo.swam.plantnursery.dao;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlaceType;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Species;

public class FakeModelFactory {
	
	private static final long MIN_RND_DATE = LocalDate.of(1970, 1, 1).toEpochDay();
	private static final long MAX_RND_DATE = LocalDate.of(2021, 8, 15).toEpochDay();

	private FakeModelFactory() {

	}
	
	private static LocalDate generateRandomDate() {
	    long randomDay = RandomUtils.nextLong(MIN_RND_DATE, MAX_RND_DATE);
	    return LocalDateTime.ofInstant(Instant.ofEpochMilli(randomDay), ZoneId.systemDefault()).toLocalDate();
	}

	public static GrowthPlace growthPlace() {
		GrowthPlace growthPlace = ModelFactory.growthPlace();
		growthPlace.setName(RandomStringUtils.randomAlphanumeric(6));
		growthPlace.setLatitude(RandomUtils.nextFloat());
		growthPlace.setLongitude(RandomUtils.nextFloat());
		GrowthPlaceType[] types = GrowthPlaceType.values();
		growthPlace.setType(types[RandomUtils.nextInt(0, types.length)]);

		return growthPlace;
	}

	public static Plant plant() {
		Species species = ModelFactory.species();
		
		Plant plant = ModelFactory.plant();
		plant.setPlantingDate(generateRandomDate());
		plant.setSpecies(species);
		
		return plant;
	}
}
