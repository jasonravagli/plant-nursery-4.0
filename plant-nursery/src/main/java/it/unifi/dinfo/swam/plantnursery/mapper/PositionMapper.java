package it.unifi.dinfo.swam.plantnursery.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotSupportedException;

import it.unifi.dinfo.swam.plantnursery.dao.GrowthPlaceDao;
import it.unifi.dinfo.swam.plantnursery.dao.PlantDao;
import it.unifi.dinfo.swam.plantnursery.dao.SensorDao;
import it.unifi.dinfo.swam.plantnursery.dto.PositionDto;
import it.unifi.dinfo.swam.plantnursery.model.Plant;
import it.unifi.dinfo.swam.plantnursery.model.Position;
import it.unifi.dinfo.swam.plantnursery.model.Sensor;

@Dependent
public class PositionMapper extends BaseMapper<Position, PositionDto> {

	@Inject
	private GrowthPlaceDao growthPlaceDao;

	@Inject
	private PlantDao plantDao;

	@Inject
	private SensorDao sensorDao;

	@Override
	public PositionDto toDto(Position obj) {
		if (obj == null) {
			throw new IllegalArgumentException("The object to convert cannot be null");
		}

		PositionDto dto = new PositionDto();
		dto.setId(obj.getId());
		dto.setRowIndex(obj.getRowIndex());
		dto.setColIndex(obj.getColumnIndex());
		dto.setGrowthPlaceId(obj.getGrowthPlace().getId());
		dto.setGrowthPlaceName(obj.getGrowthPlace().getName());
		if (obj.getPlant() != null)
			dto.setPlantId(obj.getPlant().getId());
		if (obj.getSensors() != null && obj.getSensors().size() > 0)
			dto.setListSensorsId(obj.getSensors().stream().map(Sensor::getId).collect(Collectors.toList()));

		return dto;
	}

	@Override
	public Position toEntity(PositionDto dto) {
		throw new NotSupportedException();
	}

	@Override
	public Position updateEntity(Position obj, PositionDto dto) {
		if (obj == null) {
			throw new IllegalArgumentException("The object to update cannot be null");
		}
		
		if (dto == null) {
			throw new IllegalArgumentException("The dto cannot be null");
		}
		
		
		Plant plant = null;
		if (dto.getPlantId() != null) {
			plant = plantDao.findById(dto.getPlantId());
			if(plant == null)
				throw new IllegalArgumentException("The plant does not exists");
		}

		List<Sensor> sensors = new ArrayList<>();
		if (dto.getListSensorsId() != null) {
			for (Long idSensor : dto.getListSensorsId()) {
				Sensor sensor = sensorDao.findById(idSensor);
				if(sensor == null) {
					throw new IllegalArgumentException("One of the sensors does not exists");
				}
				sensors.add(sensor);
			}
		}
		
		obj.setRowIndex(dto.getRowIndex());
		obj.setColumnIndex(dto.getColIndex());
		obj.setGrowthPlace(growthPlaceDao.findById(dto.getGrowthPlaceId()));
		obj.setPlant(plant);
		obj.setSensors(sensors);

		return obj;
	}

}
