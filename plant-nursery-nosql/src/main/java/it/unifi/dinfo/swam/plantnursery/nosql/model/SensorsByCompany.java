package it.unifi.dinfo.swam.plantnursery.nosql.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Id;

public class SensorsByCompany extends BaseEntity{
	
	@Column("mac_address")
	private String macAddress;
	
	@Id("company")
	private String company;
	
	@Column("model")
	private String model;
	
	@Column("serial_number")
	private String serialNumber;
	
	@Column("installation_date")
	private LocalDate installationDate;
	
	@Column("disposal_date")
	private LocalDate disposalDate;
	
	@Column("measure_types")
	private Set<String> measureTypes;
	
	@Column("fault_periods")
	private Set<String> faultPeriods;
	
	@Column("id_growth_place")
	private UUID idGrowthPlace;

	@Column("active")
	private boolean active;

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public LocalDate getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(LocalDate installationDate) {
		this.installationDate = installationDate;
	}

	public LocalDate getDisposalDate() {
		return disposalDate;
	}

	public void setDisposalDate(LocalDate disposalDate) {
		this.disposalDate = disposalDate;
	}

	public Set<String> getMeasureTypes() {
		return measureTypes;
	}

	public void setMeasureTypes(Set<String> measureTypes) {
		this.measureTypes = measureTypes;
	}

	public Set<String> getFaultPeriods() {
		return faultPeriods;
	}

	public void setFaultPeriods(Set<String> faultPeriods) {
		this.faultPeriods = faultPeriods;
	}
	
	public UUID getIdGrowthPlace() {
		return idGrowthPlace;
	}

	public void setIdGrowthPlace(UUID idGrowthPlace) {
		this.idGrowthPlace = idGrowthPlace;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
