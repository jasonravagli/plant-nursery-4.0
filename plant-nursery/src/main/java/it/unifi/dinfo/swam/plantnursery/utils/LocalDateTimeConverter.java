package it.unifi.dinfo.swam.plantnursery.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.ext.ParamConverter;

public class LocalDateTimeConverter implements ParamConverter<LocalDateTime> {
	
	@Override
	public LocalDateTime fromString(String value) {
		if (value == null)
			return null;
		return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
	}

	@Override
	public String toString(LocalDateTime value) {
		if (value == null)
			return null;
		return value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
	}
}
