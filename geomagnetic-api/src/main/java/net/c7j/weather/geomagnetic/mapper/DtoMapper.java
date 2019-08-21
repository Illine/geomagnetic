package net.c7j.weather.geomagnetic.mapper;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface DtoMapper<E, D> {

    Optional<E> convertToEntity(D dto);

    Optional<E> convertToEntity(D dto, E entity);

    Optional<D> convertToDto(E entity);

    Optional<D> convertToDto(E entity, D dto);

    Stream<E> convertToEntity(Stream<D> stream);

    Stream<E> convertToEntity(Collection<D> collection);

    Stream<D> convertToDto(Stream<E> stream);

    Stream<D> convertToDto(Collection<E> collection);
}