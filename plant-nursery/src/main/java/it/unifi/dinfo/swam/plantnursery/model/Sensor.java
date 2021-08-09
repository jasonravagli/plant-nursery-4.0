package it.unifi.dinfo.swam.plantnursery.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "sensors")
public class Sensor extends BaseEntity{
	
	@Column(unique = true)
	private String macAddress;
	private String company;
	private String model;
	private String serialNumber;
	private Date installationDate;
	private Date disposalDate;
	
	@ElementCollection
	@CollectionTable(name = "sensor_measure_types", joinColumns = @JoinColumn(name = "sensor_id"))
	@Enumerated(EnumType.STRING)
	private Set<MeasureType> measureTypes;
	
	@ElementCollection
	@CollectionTable(name = "fault_periods", joinColumns = @JoinColumn(name = "sensor_id"))
	private List<FaultPeriod> faultPeriods;

	protected Sensor() {
	}
	
	public Sensor(String uuid) {
		super(uuid);
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

	public Date getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	public Date getDisposalDate() {
		return disposalDate;
	}

	public void setDisposalDate(Date disposalDate) {
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

	public List<FaultPeriod> getFaultPeriods() {
		return faultPeriods;
	}

	public void setFaultPeriods(List<FaultPeriod> faultPeriods) {
		this.faultPeriods = faultPeriods;
	}
	
	public void addFaultPeriod(FaultPeriod period) {
		this.faultPeriods.add(period);
	}
	
	public void removeFaultPeriod(FaultPeriod period) {
		this.faultPeriods.remove(period);
	}
}
