package it.unifi.dinfo.swam.plantnursery.utils;

import java.time.LocalDate;

import jakarta.ws.rs.ext.ParamConverter;

public class LocalDateConverter implements ParamConverter<LocalDate> {

	@Override
	public LocalDate fromString(String value) {
		if (value == null)
			return null;
		return LocalDate.parse(value);
	}

	@Override
	public String toString(LocalDate value) {
		if (value == null)
			return null;
		return value.toString();
	}
}