package it.unifi.dinfo.swam.plantnursery.model;

import java.sql.Date;

import javax.persistence.Embeddable;

@Embeddable
public class FaultPeriod {
	
	private Date startDate;
	private Date endDate;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
