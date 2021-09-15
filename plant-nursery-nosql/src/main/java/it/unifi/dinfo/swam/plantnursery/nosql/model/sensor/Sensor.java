package it.unifi.dinfo.swam.plantnursery.nosql.model.sensor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface Sensor {
	
	public UUID getId();
	
	public void setId(UUID id);
	
	public String getMacAddress();

	public void setMacAddress(String macAddress);

	public String getCompany();

	public void setCompany(String company);

	public String getModel();

	public void setModel(String model);

	public String getSerialNumber();

	public void setSerialNumber(String serialNumber);

	public LocalDate getInstallationDate();

	public void setInstallationDate(LocalDate installationDate);

	public LocalDate getDisposalDate();

	public void setDisposalDate(LocalDate disposalDate);

	public Set<String> getMeasureTypes();

	public void setMeasureTypes(Set<String> measureTypes);

	public Set<String> getFaultPeriods();

	public void setFaultPeriods(Set<String> faultPeriods);
	
	public UUID getIdGrowthPlace();

	public void setIdGrowthPlace(UUID idGrowthPlace);
}
