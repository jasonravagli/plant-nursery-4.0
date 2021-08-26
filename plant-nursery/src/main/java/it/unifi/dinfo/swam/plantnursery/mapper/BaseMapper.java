package it.unifi.dinfo.swam.plantnursery.mapper;

import it.unifi.dinfo.swam.plantnursery.model.BaseEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseMapper<T extends BaseEntity, dtoT> {

	public abstract dtoT toDto(T obj) throws IllegalArgumentException;

	public abstract T toEntity(dtoT dto) throws IllegalArgumentException;

	public abstract T updateEntity(T obj, dtoT dto) throws IllegalArgumentException;

	public List<dtoT> toDto(Collection<T> list) throws IllegalArgumentException {
		return list.stream().map(this::toDto).collect(Collectors.toList());
	}
}
