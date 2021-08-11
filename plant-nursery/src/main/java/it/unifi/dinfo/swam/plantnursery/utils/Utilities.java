package it.unifi.dinfo.swam.plantnursery.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Utilities {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Date getDateFromString(String strDate) {
		try {
			return new Date(DATE_FORMAT.parse(strDate).getTime());
		} catch (ParseException e) {
			System.err.println("Error parsing string '" + strDate + "' to date");
			return null;
		}
	}
}
