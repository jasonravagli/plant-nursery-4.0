package it.unifi.dinfo.swam.plantnursery.nosql.mapper;

import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.MeasurementDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.measurement.Measurement;
import jakarta.enterprise.context.Dependent;

@Dependent
public class MeasurementMapper {

	public <T extends Measurement> T toEntity(UUID id, MeasurementDto dto, UUID idGrowthPlace,
			UUID idPos, UUID idPlant, UUID idSensor, Class<T> type) throws InstantiationException, IllegalAccessException {
		Measurement entity = type.newInstance();
		entity.setId(id);
		entity.setIdGrowthPlace(idGrowthPlace);
		entity.setIdPlant(idPlant);
		entity.setIdPosition(idPos);
		entity.setIdSensor(idSensor);
		entity.setMeasDate(dto.getDate());
		entity.setType(dto.getType());
		entity.setUnit(dto.getUnit());
		entity.setValue(dto.getValue());

		return type.cast(entity);
	}

}
