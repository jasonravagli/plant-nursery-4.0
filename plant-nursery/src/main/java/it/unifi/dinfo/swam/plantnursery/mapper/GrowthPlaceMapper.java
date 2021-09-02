package it.unifi.dinfo.swam.plantnursery.mapper;

import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.NotSupportedException;

import it.unifi.dinfo.swam.plantnursery.dto.GrowthPlaceDto;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlace;
import it.unifi.dinfo.swam.plantnursery.model.GrowthPlaceType;
import it.unifi.dinfo.swam.plantnursery.model.ModelFactory;
import it.unifi.dinfo.swam.plantnursery.model.Position;

@Dependent
public class GrowthPlaceMapper extends BaseMapper<GrowthPlace, GrowthPlaceDto> {

	@Override
	public GrowthPlaceDto toDto(GrowthPlace obj) {
		throw new NotSupportedException();
	}

	public GrowthPlaceDto toDto(GrowthPlace obj, List<Position> positions) {
		if(obj == null) {
			throw new IllegalArgumentException("The object to convert cannot be null");
		}
		
		GrowthPlaceDto dto = new GrowthPlaceDto();
		dto.setId(obj.getId());
		dto.setName(obj.getName());
		dto.setLatitude(obj.getLatitude());
		dto.setLongitude(obj.getLongitude());
		dto.setType(obj.getType().toString());
		
		int nRows = 0;
		int nCols = 0;
		if(positions == null || positions.size() > 0) {
			nRows = positions.stream().mapToInt(p -> p.getRowIndex()).max().getAsInt() + 1;
			nCols = positions.stream().mapToInt(p -> p.getColumnIndex()).max().getAsInt() + 1;
		}
		dto.setRowsPositions(nRows);
		dto.setColumnsPositions(nCols);
		
		return dto;
	}

	@Override
	public GrowthPlace toEntity(GrowthPlaceDto dto) {
		if(dto == null) {
			throw new IllegalArgumentException("The dto to convert cannot be null");
		}
		
		GrowthPlace growthPlace = ModelFactory.growthPlace();
		
		growthPlace.setName(dto.getName());
		growthPlace.setType(GrowthPlaceType.valueOf(dto.getType()));
		growthPlace.setLatitude(dto.getLatitude());
		growthPlace.setLongitude(dto.getLongitude());
		
		return growthPlace;
	}

	@Override
	public GrowthPlace updateEntity(GrowthPlace obj, GrowthPlaceDto dto) {
		if(obj == null) {
			throw new IllegalArgumentException("The object to update cannot be null");
		}
		if(dto == null) {
			throw new IllegalArgumentException("The dto to convert cannot be null");
		}
		
		obj.setName(dto.getName());
		obj.setType(GrowthPlaceType.valueOf(dto.getType()));
		obj.setLatitude(dto.getLatitude());
		obj.setLongitude(dto.getLongitude());
		
		return obj;
	}
}
