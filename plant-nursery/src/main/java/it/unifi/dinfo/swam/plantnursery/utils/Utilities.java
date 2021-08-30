package it.unifi.dinfo.swam.plantnursery.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import tech.tablesaw.api.Table;

public class Utilities {
	
	private Utilities() {	
	}
	
	public static boolean areDateEqualsOrBothNull(LocalDate date1, LocalDate date2) {
		if(date1 == null && date2 == null)
			return true;
		
		if((date1 == null && date2 != null) || (date1 != null && date2 == null))
			return false;
		
		if(date1.equals(date2))
			return true;
		
		return false;
	}
	
	public static boolean areDateEqualsOrBothNull(LocalDateTime date1, LocalDateTime date2) {
		if(date1 == null && date2 == null)
			return true;
		
		if((date1 == null && date2 != null) || (date1 != null && date2 == null))
			return false;
		
		if(date1.equals(date2))
			return true;
		
		return false;
	}
	
	public static String convertTableToCsvString(Table table) throws UnsupportedEncodingException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		table.write().csv(bos);
		return new String(bos.toByteArray(), "UTF-8");
	}
}
