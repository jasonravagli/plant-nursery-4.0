package it.unifi.dinfo.swam.plantnursery.utils;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LocalDateJacksonDeserializer extends StdDeserializer<LocalDate> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6984490246351574198L;
	
	public LocalDateJacksonDeserializer() {
        this(null);
    }

    public LocalDateJacksonDeserializer(Class<?> vc) {
        super(vc);
    }

	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		String value = p.getText();
		
		if (value == null)
			return null;
		
		return LocalDate.parse(value);
	}

}
