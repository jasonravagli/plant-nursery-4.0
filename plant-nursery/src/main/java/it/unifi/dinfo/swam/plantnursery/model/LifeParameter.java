package it.unifi.dinfo.swam.plantnursery.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class LifeParameter {

	private float rangeStart;
	private float rangeEnd;

	@Enumerated(EnumType.STRING)
	private Unit unit;

	@Enumerated(EnumType.STRING)
	private MeasureType type;

	public float getRangeStart() {
		return rangeStart;
	}

	public void setRangeStart(float rangeStart) {
		this.rangeStart = rangeStart;
	}

	public float getRangeEnd() {
		return rangeEnd;
	}

	public void setRangeEnd(float rangeEnd) {
		this.rangeEnd = rangeEnd;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public MeasureType getType() {
		return type;
	}

	public void setType(MeasureType type) {
		this.type = type;
	}

}
