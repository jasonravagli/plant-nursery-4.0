package it.unifi.dinfo.swam.plantnursery.utils;

import java.time.LocalDateTime;

import javax.ws.rs.ext.ParamConverter;

public class LocalDateTimeConverter implements ParamConverter<LocalDateTime> {
	
	@Override
	public LocalDateTime fromString(String value) {
		if (value == null)
			return null;
		return LocalDateTime.parse(value);
	}

	@Override
	public String toString(LocalDateTime value) {
		if (value == null)
			return null;
		return value.toString();
	}
}
