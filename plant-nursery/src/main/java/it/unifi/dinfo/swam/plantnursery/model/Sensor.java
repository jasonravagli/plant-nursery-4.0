package it.unifi.dinfo.swam.plantnursery.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "sensors")
public class Sensor extends BaseEntity{
	
	@Column(unique = true)
	private String macAddress;
	private String company;
	private String model;
	private String serialNumber;
	private LocalDate installationDate;
	private LocalDate disposalDate;
	
	@ElementCollection
	@CollectionTable(name = "sensor_measure_types", joinColumns = @JoinColumn(name = "sensor_id"))
	@Enumerated(EnumType.STRING)
	private Set<MeasureType> measureTypes;
	
	@ElementCollection
	@CollectionTable(name = "fault_periods", joinColumns = @JoinColumn(name = "sensor_id"))
	private Set<FaultPeriod> faultPeriods;

	protected Sensor() {
		measureTypes = new HashSet<>();
		faultPeriods = new HashSet<>();
	}
	
	public Sensor(String uuid) {
		super(uuid);
		
		measureTypes = new HashSet<>();
		faultPeriods = new HashSet<>();
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
	
	public void addMeasureType(MeasureType measureType) {
		this.measureTypes.add(measureType);
	}
	
	public void removeMeasureType(MeasureType measureType) {
		this.measureTypes.remove(measureType);
	}

	public Set<FaultPeriod> getFaultPeriods() {
		return faultPeriods;
	}

	public void setFaultPeriods(Set<FaultPeriod> faultPeriods) {
		this.faultPeriods = faultPeriods;
	}
	
	public void addFaultPeriod(FaultPeriod period) {
		this.faultPeriods.add(period);
	}
	
	public void removeFaultPeriod(FaultPeriod period) {
		this.faultPeriods.remove(period);
	}
}
