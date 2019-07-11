package net.c7j.weather.geomagnetic.mapper;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

@Slf4j(topic = "GEOMAGNETIC-MAPPER")
public abstract class AbstractDtoMapper<E, D> implements DtoMapper<E, D> {

    protected final ModelMapper modelMapper;

    protected Class<E> entityClass;
    protected Class<D> dtoClass;

    protected AbstractDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        init();
        setupMapper();
    }

    @Override
    public Optional<E> convertToEntity(D dto) {
        if (Objects.isNull(dto)) {
            LOGGER.warn("A 'dto' is null! Return empty optional");
            return Optional.empty();
        } else {
            LOGGER.debug("Map to an 'entity'. A 'dto': {}", dto);
            return Optional.of(modelMapper.map(dto, entityClass));
        }
    }

    @Override
    public Optional<E> convertToEntity(D dto, E entity) {
        if (Objects.isNull(dto)) {
            LOGGER.warn("A 'dto' is null! Return an 'entity' optional");
            return Optional.ofNullable(entity);
        } else if (Objects.isNull(entity)) {
            LOGGER.warn("An 'entity' is null! Map via a 'dto'!");
            return convertToEntity(dto);
        }
        LOGGER.debug("Update map to an 'entity'. The 'entity': {}, a 'dto': {}", entity, dto);
        modelMapper.map(dto, entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<D> convertToDto(E entity) {
        if (Objects.isNull(entity)) {
            LOGGER.warn("An 'entity' is null! Return empty optional");
            return Optional.empty();
        } else {
            LOGGER.debug("Map to a 'dto'. An 'entity': {}", entity);
            return Optional.of(modelMapper.map(entity, dtoClass));
        }
    }

    @Override
    public Optional<D> convertToDto(E entity, D dto) {
        if (Objects.isNull(entity)) {
            LOGGER.warn("An 'entity' is null! Return a 'dto' optional");
            return Optional.ofNullable(dto);
        } else if (Objects.isNull(dto)) {
            LOGGER.warn("A 'dto' is null! Map via an 'entity'!");
            return convertToDto(entity);
        }
        LOGGER.debug("Update map to a 'dto'. The 'dto': {}, an 'entity': {}", dto, entity);
        modelMapper.map(entity, dto);
        return Optional.of(dto);
    }

    @Override
    public Stream<E> convertToEntity(Stream<D> stream) {
        LOGGER.debug("Map to a stream of entities");
        return stream
                .map(this::convertToEntity)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    @Override
    public Stream<E> convertToEntity(Collection<D> collection) {
        return convertToEntity(collection.stream());
    }

    @Override
    public Stream<D> convertToDto(Stream<E> stream) {
        Assert.notNull(stream, "The 'stream' shouldn't be null!");
        LOGGER.debug("Map to a stream of dtos");
        return stream
                .map(this::convertToDto)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    @Override
    public Stream<D> convertToDto(Collection<E> collection) {
        Assert.notNull(collection, "The 'collection' shouldn't be null!");
        return convertToDto(collection.stream());
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