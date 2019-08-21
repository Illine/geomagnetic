package net.c7j.weather.geomagnetic.test.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.c7j.weather.geomagnetic.dao.dto.ForecastDto;
import net.c7j.weather.geomagnetic.dao.dto.ForecastResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.function.*;

import static org.junit.jupiter.api.Assertions.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssertionHelper {

    public static BiConsumer<ResponseEntity<String>, HttpStatus> assertCall(Predicate<String> predicate) {
        return (responseEntity, status) -> {
            assertEquals(status, responseEntity.getStatusCode());
            assertFalse(predicate.test(responseEntity.getBody()));
        };
    }

    public static <T, R> Consumer<R> assertCall(Supplier<T> supplier) {
        return expected -> assertEquals(expected, supplier.get());
    }

    public static Function<ResponseEntity<ForecastResponse>, List<ForecastDto>> assertCall(int expectedCountForecasts) {
        return responseEntity -> {
            assertNotNull(responseEntity);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

            var response = responseEntity.getBody();
            assertNotNull(response);
            assertTrue(response.isSuccess());
            assertNotNull(response.getMessage());

            var forecasts = response.getGeomagneticForecasts();
            assertNotNull(forecasts);
            assertEquals(expectedCountForecasts, forecasts.size());
            return forecasts;
        };
    }

    public static BiConsumer<ResponseEntity<ForecastResponse>, HttpStatus> assertCall(String errorMessage) {
        return (responseEntity, status) -> {
            assertNotNull(responseEntity);
            assertEquals(status, responseEntity.getStatusCode());
            assertNotNull(responseEntity.getBody());
            assertEquals(errorMessage, responseEntity.getBody().getMessage());
        };
    }
}