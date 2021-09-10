package it.unifi.dinfo.swam.plantnursery.nosql.dto;

public class LifeParameterDto extends BaseDto {

	private float rangeStart;
	private float rangeEnd;
	private Unit unit;
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
	
	@Override
	public int hashCode() {
		int hash = 7;
	    hash = 31 * hash + Float.hashCode(rangeStart);
	    hash = 31 * hash + Float.hashCode(rangeEnd);
	    hash = 31 * hash + unit.hashCode();
	    hash = 31 * hash + type.hashCode();
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
		LifeParameterDto other = (LifeParameterDto) obj;
		return Float.floatToIntBits(rangeEnd) == Float.floatToIntBits(other.rangeEnd)
				&& Float.floatToIntBits(rangeStart) == Float.floatToIntBits(other.rangeStart) && type == other.type
				&& unit == other.unit;
	}

}
