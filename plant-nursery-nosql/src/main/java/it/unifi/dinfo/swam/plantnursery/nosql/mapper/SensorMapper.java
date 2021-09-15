package it.unifi.dinfo.swam.plantnursery.nosql.mapper;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import it.unifi.dinfo.swam.plantnursery.nosql.dto.FaultPeriodDto;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.MeasureType;
import it.unifi.dinfo.swam.plantnursery.nosql.dto.SensorDto;
import it.unifi.dinfo.swam.plantnursery.nosql.model.sensor.Sensor;
import jakarta.enterprise.context.Dependent;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

@Dependent
public class SensorMapper {
	
	public SensorDto toDto(Sensor entity) {
		SensorDto dto = new SensorDto();
		dto.setId(entity.getId());
		dto.setCompany(entity.getCompany());
		dto.setModel(entity.getModel());
		dto.setSerialNumber(entity.getSerialNumber());
		dto.setMacAddress(entity.getMacAddress());
		dto.setDisposalDate(entity.getDisposalDate());
		dto.setInstallationDate(entity.getInstallationDate());

		Set<MeasureType> measureTypes = entity.getMeasureTypes().stream().map(t -> MeasureType.valueOf(t))
				.collect(Collectors.toSet());
		dto.setMeasureTypes(measureTypes);

		Jsonb jsonb = JsonbBuilder.create();
		Set<FaultPeriodDto> faultPeriods = entity.getFaultPeriods().stream()
				.map(s -> jsonb.fromJson(s, FaultPeriodDto.class)).collect(Collectors.toSet());
		dto.setFaultPeriods(faultPeriods);

		return dto;
	}
	
	public <T extends Sensor> List<SensorDto> toDto(List<T> entities){
		return entities.stream().map(this::toDto).collect(Collectors.toList());
	}

	public <T extends Sensor> T toEntity(UUID id, SensorDto dto, Class<T> type) throws InstantiationException, IllegalAccessException {
		Sensor entity = type.newInstance();
		entity.setId(id);
		entity.setCompany(dto.getCompany());
		entity.setModel(dto.getModel());
		entity.setSerialNumber(dto.getSerialNumber());
		entity.setMacAddress(dto.getMacAddress());
		entity.setInstallationDate(dto.getInstallationDate());
		entity.setDisposalDate(dto.getDisposalDate());

		Set<String> measureTypes = dto.getMeasureTypes().stream().map(t -> t.toString()).collect(Collectors.toSet());
		entity.setMeasureTypes(measureTypes);

		Set<String> faultPeriods = dto.getFaultPeriods().stream().map(fp -> fp.toString()).collect(Collectors.toSet());
		entity.setFaultPeriods(faultPeriods);
		
		return type.cast(entity);
	}
}
