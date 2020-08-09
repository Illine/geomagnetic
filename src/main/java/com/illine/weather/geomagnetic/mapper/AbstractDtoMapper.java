package com.illine.weather.geomagnetic.mapper;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Slf4j(topic = "GEOMAGNETIC-MAPPER")
public abstract class AbstractDtoMapper<S, D> implements DtoMapper<S, D> {

    private static final String NOT_NULL_DESTINATION_ERROR_MESSAGE = "A destination shouldn't be null!";
    private static final String NOT_NULL_SOURCE_ERROR_MESSAGE = "An source shouldn't be null!";
    private static final String NOT_NULL_COLLECTION_ERROR_MESSAGE = "A collection shouldn't be null!";

    protected final ModelMapper modelMapper;

    protected Class<S> sourceClass;
    protected Class<D> destinationClass;

    protected AbstractDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        init();
        setupMapper();
    }

    @Override
    public S convertToSource(D destination) {
        Assert.notNull(destination, NOT_NULL_DESTINATION_ERROR_MESSAGE);
        LOGGER.debug("Map to an 'entity'. A 'dto': {}", destination);
        return modelMapper.map(destination, sourceClass);
    }

    @Override
    public D convertToDestination(S source) {
        Assert.notNull(source, NOT_NULL_SOURCE_ERROR_MESSAGE);
        LOGGER.debug("Map to a 'dto'. An 'entity': {}", source);
        return modelMapper.map(source, destinationClass);
    }

    @Override
    public List<S> convertToSources(Collection<D> collection) {
        Assert.notNull(collection, NOT_NULL_COLLECTION_ERROR_MESSAGE);
        LOGGER.debug("A collection of dtos is mapped to a collection of entities");
        return collection.stream().map(this::convertToSource).collect(Collectors.toList());
    }

    @Override
    public List<D> convertToDestinations(Collection<S> collection) {
        Assert.notNull(collection, NOT_NULL_COLLECTION_ERROR_MESSAGE);
        LOGGER.debug("A collection of entities is mapped to a collection of dtos");
        return collection.stream().map(this::convertToDestination).collect(Collectors.toList());
    }

    protected Converter<S, D> toDestinationConverter(BiConsumer<S, D> toDestinationConverter) {
        return context -> {
            var source = context.getSource();
            var destination = context.getDestination();
            toDestinationConverter.accept(source, destination);
            return destination;
        };
    }

    protected Converter<D, S> toSourceConverter(BiConsumer<D, S> toSourceConverter) {
        return context -> {
            var source = context.getSource();
            var destination = context.getDestination();
            toSourceConverter.accept(source, destination);
            return destination;
        };
    }

    protected abstract void setupMapper();

    @SuppressWarnings("unchecked")
    private void init() {
        sourceClass = (Class<S>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        destinationClass = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
}