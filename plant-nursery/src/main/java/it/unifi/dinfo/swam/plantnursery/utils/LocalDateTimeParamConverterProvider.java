package it.unifi.dinfo.swam.plantnursery.utils;

import java.time.LocalDateTime;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class LocalDateTimeParamConverterProvider  implements ParamConverterProvider {

	@SuppressWarnings("unchecked")
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
		if (rawType.equals(LocalDateTime.class))
			return (ParamConverter<T>) new LocalDateTimeConverter();
		return null;
	}
}
