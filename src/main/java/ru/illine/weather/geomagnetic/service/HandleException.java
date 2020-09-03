package ru.illine.weather.geomagnetic.service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface HandleException {

    default <T extends RuntimeException> void throwWhen(Set<?> set,
                                                        Predicate<Set<?>> predicate,
                                                        Supplier<T> exception) {
        if (predicate.test(set)) {
            throw exception.get();
        }
    }

    default <T extends RuntimeException> void throwWhen(List<?> list,
                                                        Predicate<List<?>> predicate,
                                                        Supplier<T> exception) {
        if (predicate.test(list)) {
            throw exception.get();
        }
    }
}