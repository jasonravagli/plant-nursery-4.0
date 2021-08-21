package it.unifi.dinfo.swam.plantnursery.model;


import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

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

}
