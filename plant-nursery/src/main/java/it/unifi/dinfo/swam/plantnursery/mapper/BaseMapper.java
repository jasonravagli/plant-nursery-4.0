package it.unifi.dinfo.swam.plantnursery.mapper;

import it.unifi.dinfo.swam.plantnursery.model.BaseEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseMapper<T extends BaseEntity, dtoT> {

	public abstract dtoT convert(T obj);

	public List<dtoT> convert(Collection<T> list) {
		return list.stream().map(this::convert).collect(Collectors.toList());
	}
}
