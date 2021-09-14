package it.unifi.dinfo.swam.plantnursery.nosql.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.unifi.dinfo.swam.plantnursery.utils.LocalDateJacksonDeserializer;
import it.unifi.dinfo.swam.plantnursery.utils.LocalDateJacksonSerializer;

public class SensorDto {

	private UUID id;
	private String macAddress;
	private String company;
	private String model;
	private String serialNumber;
	
	@JsonSerialize(using = LocalDateJacksonSerializer.class)
	@JsonDeserialize(using = LocalDateJacksonDeserializer.class)
	private LocalDate installationDate;
	
	@JsonSerialize(using = LocalDateJacksonSerializer.class)
	@JsonDeserialize(using = LocalDateJacksonDeserializer.class)
	private LocalDate disposalDate;
	
	private Set<MeasureType> measureTypes;
	private Set<FaultPeriodDto> faultPeriods;
	
	public SensorDto() {
		measureTypes = new HashSet<MeasureType>();
		faultPeriods = new HashSet<FaultPeriodDto>();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public Set<MeasureType> getMeasureTypes() {
		return measureTypes;
	}

	public void setMeasureTypes(Set<MeasureType> measureTypes) {
		this.measureTypes = measureTypes;
	}

	public Set<FaultPeriodDto> getFaultPeriods() {
		return faultPeriods;
	}

	public void setFaultPeriods(Set<FaultPeriodDto> faultPeriods) {
		this.faultPeriods = faultPeriods;
	}
}
