package net.c7j.weather.geomagnetic.service;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface DtoMapperChecker {

    default <T> void whenNotNull(T o, Consumer<T> c) {
        when(o, Objects::nonNull, c);
    }

    default <T> void when(T o, Predicate<T> p, Consumer<T> c) {
        if (p.test(o)) {
            c.accept(o);
        }
    }
}