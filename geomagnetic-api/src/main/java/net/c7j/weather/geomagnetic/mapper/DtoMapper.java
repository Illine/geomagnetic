package net.c7j.weather.geomagnetic.mapper;

import java.util.Collection;

public interface DtoMapper<E, D> {

    E convertToEntity(D dto);

    E convertToEntity(D dto, E entity);

    D convertToDto(E entity);

    D convertToDto(E entity, D dto);

    Collection<E> convertToEntities(Collection<D> collection);

    Collection<D> convertToDtos(Collection<E> collection);
}