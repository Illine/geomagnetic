package net.c7j.weather.geomagnetic.mapper;

import java.util.Collection;

public interface DtoMapper<S, D> {

    S convertToSource(D destination);

    S convertToSource(D destination, S source);

    D convertToDestination(S source);

    D convertToDestination(S source, D destination);

    Collection<S> convertToSources(Collection<D> collection);

    Collection<D> convertToDestinations(Collection<S> collection);
}