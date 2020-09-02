package ru.illine.weather.geomagnetic.test.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssertionHelper {

    public static <T, R> Consumer<R> assertCall(Supplier<T> supplier) {
        return expected -> assertEquals(expected, supplier.get());
    }
}