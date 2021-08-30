package it.unifi.dinfo.swam.plantnursery.mapper;

import javax.enterprise.context.Dependent;

import it.unifi.dinfo.swam.plantnursery.dto.MeasurementDto;
import it.unifi.dinfo.swam.plantnursery.model.MeasureType;
import it.unifi.dinfo.swam.plantnursery.model.Measurement;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Sensor;
import it.unifi.dinfo.swam.plantnursery.model.Unit;

@Dependent
public class MeasurementMapper {
	
	public Measurement toEntity(MeasurementDto dto, Position posMeasurement,
			Sensor sensorMeasurement) throws IllegalArgumentException {
		Measurement meas = ModelFactory.measurement();
		meas.setDate(dto.getDate());
		meas.setType(MeasureType.valueOf(dto.getType()));
		meas.setUnit(Unit.valueOf(dto.getUnit()));
		meas.setValue(dto.getValue());
		meas.setPlant(posMeasurement.getPlant());
		meas.setSensor(sensorMeasurement);
		meas.setPosition(posMeasurement);
		
		return meas;
	}

}
