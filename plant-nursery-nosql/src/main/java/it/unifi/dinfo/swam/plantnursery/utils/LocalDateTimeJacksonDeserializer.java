package it.unifi.dinfo.swam.plantnursery.utils;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LocalDateTimeJacksonDeserializer extends StdDeserializer<LocalDateTime> {
	
	private static final long serialVersionUID = 4518634539025016079L;

	public LocalDateTimeJacksonDeserializer() {
        this(null);
    }

    public LocalDateTimeJacksonDeserializer(Class<?> vc) {
        super(vc);
    }

	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		String value = p.getText();
		
		if (value == null)
			return null;
		
		return LocalDateTime.parse(value);
	}
}
