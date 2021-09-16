package it.unifi.dinfo.swam.plantnursery.nosql.mapper;

import java.util.UUID;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.MeasuramentDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.measurament.Measurament;

public class MeasuramentsMapper {

	public <T extends Measurament> T toEntity(UUID id, MeasuramentDto dto, UUID idGrowthPlace,
			UUID idPos, UUID idPlant, UUID idSensor, Class<T> type) throws InstantiationException, IllegalAccessException {
		Measurament entity = type.newInstance();
		entity.setId(id);
		entity.setIdGrowthPlace(idGrowthPlace);
		entity.setIdPlant(idPlant);
		entity.setIdPosition(idPos);
		entity.setIdSensor(idSensor);
		entity.setMeasDate(dto.getMeasuramentDate());
		entity.setType(dto.getType());
		entity.setUnit(dto.getUnit());
		entity.setValue(dto.getValue());

		return type.cast(entity);
	}

}
