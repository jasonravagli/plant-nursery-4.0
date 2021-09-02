package it.unifi.dinfo.swam.plantnursery.model;


import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class FaultPeriod {
	
	@NotNull
	private LocalDateTime startDate;
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
		FaultPeriod other = (FaultPeriod) obj;
		return Objects.equals(endDate, other.endDate) && Objects.equals(startDate, other.startDate);
	}

}
