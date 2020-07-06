package com.illine.weather.geomagnetic.test.helper;

import com.illine.weather.geomagnetic.model.dto.MobileForecastResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssertionHelper {

    public static <T, R> Consumer<R> assertCall(Supplier<T> supplier) {
        return expected -> assertEquals(expected, supplier.get());
    }

    public static <T extends MobileForecastResponse> BiConsumer<ResponseEntity<T>, HttpStatus> assertCall(boolean success) {
        return (response, status) -> {
            assertNotNull(response);
            assertEquals(status, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(success, response.getBody().isSuccess());
        };
    }
}