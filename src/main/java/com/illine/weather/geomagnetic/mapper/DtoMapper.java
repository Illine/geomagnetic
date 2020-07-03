package com.illine.weather.geomagnetic.mapper;

import java.util.Collection;

public interface DtoMapper<S, D> {

    S convertToSource(D destination);

    D convertToDestination(S source);

    Collection<S> convertToSources(Collection<D> collection);

    Collection<D> convertToDestinations(Collection<S> collection);
}