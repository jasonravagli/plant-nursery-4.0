package it.unifi.dinfo.swam.plantnursery.utils;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LocalDateJacksonSerializer extends StdSerializer<LocalDate> {
    
    private static final long serialVersionUID = 486881599333800350L;

	public LocalDateJacksonSerializer() {
        this(null);
    }
  
    public LocalDateJacksonSerializer(Class<LocalDate> t) {
        super(t);
    }

	@Override
	public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		if(value == null)
			gen.writeString(String.valueOf(null));
		else
			gen.writeString(value.toString());
	}
}
