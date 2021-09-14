package it.unifi.dinfo.swam.plantnursery.nosql.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.unifi.dinfo.swam.plantnursery.utils.LocalDateTimeJacksonDeserializer;
import it.unifi.dinfo.swam.plantnursery.utils.LocalDateTimeJacksonSerializer;

public class FaultPeriodDto extends BaseDto {
	

	@JsonSerialize(using = LocalDateTimeJacksonSerializer.class)
	@JsonDeserialize(using = LocalDateTimeJacksonDeserializer.class)
	private LocalDateTime startDate;
	
	@JsonSerialize(using = LocalDateTimeJacksonSerializer.class)
	@JsonDeserialize(using = LocalDateTimeJacksonDeserializer.class)
	private LocalDateTime endDate;
	
	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		int hash = 7;
	    hash = 31 * hash + startDate.hashCode();
	    hash = 31 * hash + (endDate == null? 0 : endDate.hashCode());
	    return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FaultPeriodDto other = (FaultPeriodDto) obj;
		return Objects.equals(endDate, other.endDate) && Objects.equals(startDate, other.startDate);
	}
}
