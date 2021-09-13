package it.unifi.dinfo.swam.plantnursery.utils;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LocalDateTimeJacksonSerializer extends StdSerializer<LocalDateTime> {

	private static final long serialVersionUID = -4250867081804292823L;

	public LocalDateTimeJacksonSerializer() {
        this(null);
    }
  
    public LocalDateTimeJacksonSerializer(Class<LocalDateTime> t) {
        super(t);
    }

	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		if(value == null)
			gen.writeString(String.valueOf(null));
		else
			gen.writeString(value.toString());
	}
}
