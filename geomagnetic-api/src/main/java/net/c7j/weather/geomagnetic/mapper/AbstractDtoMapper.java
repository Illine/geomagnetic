package net.c7j.weather.geomagnetic.mapper;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Slf4j(topic = "GEOMAGNETIC-MAPPER")
public abstract class AbstractDtoMapper<E, D> implements DtoMapper<E, D> {

    private static final String NOT_NULL_DTO_ERROR_MESSAGE = "A dto shouldn't be null!";
    private static final String NOT_NULL_ENTITY_ERROR_MESSAGE = "An entity shouldn't be null!";
    private static final String NOT_NULL_COLLECTION_ERROR_MESSAGE = "A collection shouldn't be null!";

    protected final ModelMapper modelMapper;

    protected Class<E> entityClass;
    protected Class<D> dtoClass;

    protected AbstractDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        init();
        setupMapper();
    }

    @Override
    public E convertToEntity(D dto) {
        Assert.notNull(dto, NOT_NULL_DTO_ERROR_MESSAGE);
        LOGGER.debug("Map to an 'entity'. A 'dto': {}", dto);
        return modelMapper.map(dto, entityClass);
    }

    @Override
    public E convertToEntity(D dto, E entity) {
        Assert.notNull(dto, NOT_NULL_DTO_ERROR_MESSAGE);
        Assert.notNull(entity, NOT_NULL_ENTITY_ERROR_MESSAGE);
        LOGGER.debug("Update map to an 'entity'. The 'entity': {}, a 'dto': {}", entity, dto);
        modelMapper.map(dto, entity);
        return entity;
    }

    @Override
    public D convertToDto(E entity) {
        Assert.notNull(entity, NOT_NULL_ENTITY_ERROR_MESSAGE);
        LOGGER.debug("Map to a 'dto'. An 'entity': {}", entity);
        return modelMapper.map(entity, dtoClass);
    }

    @Override
    public D convertToDto(E entity, D dto) {
        Assert.notNull(entity, NOT_NULL_ENTITY_ERROR_MESSAGE);
        Assert.notNull(dto, NOT_NULL_DTO_ERROR_MESSAGE);
        LOGGER.debug("Update map to a 'dto'. The 'dto': {}, an 'entity': {}", dto, entity);
        modelMapper.map(entity, dto);
        return dto;
    }

    @Override
    public Collection<E> convertToEntities(Collection<D> collection) {
        Assert.notNull(collection, NOT_NULL_COLLECTION_ERROR_MESSAGE);
        LOGGER.debug("A collection of dtos is mapped to a collection of entities");
        return collection.stream().map(this::convertToEntity).collect(Collectors.toList());
    }

    @Override
    public Collection<D> convertToDtos(Collection<E> collection) {
        Assert.notNull(collection, NOT_NULL_COLLECTION_ERROR_MESSAGE);
        LOGGER.debug("A collection of entities is mapped to a collection of dtos");
        return collection.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    protected Converter<E, D> toDtoConverter(BiConsumer<E, D> toDtoConverter) {
        return context -> {
            var source = context.getSource();
            var destination = context.getDestination();
            toDtoConverter.accept(source, destination);
            return destination;
        };
    }

    protected Converter<D, E> toEntityConverter(BiConsumer<D, E> toEntityConverter) {
        return context -> {
            var source = context.getSource();
            var destination = context.getDestination();
            toEntityConverter.accept(source, destination);
            return destination;
        };
    }

    protected abstract void setupMapper();

    @SuppressWarnings("unchecked")
    private void init() {
        entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        dtoClass = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
}