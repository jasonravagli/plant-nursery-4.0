package it.unifi.dinfo.swam.plantnursery.nosql.model;

import it.unifi.dinfo.swam.plantnursery.model.MeasureType;
import it.unifi.dinfo.swam.plantnursery.model.Unit;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

@Embeddable
public class NoSqlLifeParameter {
	
	@Column("range_start")
	private float rangeStart;
	
	@Column("range_end")
	private float rangeEnd;
	
	@Column("unit")
	private Unit unit;
	
	@Column("type")
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
