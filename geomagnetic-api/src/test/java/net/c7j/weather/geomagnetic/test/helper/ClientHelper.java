package net.c7j.weather.geomagnetic.test.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.c7j.weather.geomagnetic.dao.dto.ForecastResponse;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientHelper {

    private static final String URI_GET_DIURNAL = "/geomagnetic/forecasts/get/diurnal";
    private static final String URI_GET_CURRENT = "/geomagnetic/forecasts/get/current";
    private static final String URI_GET_THREE_DAY = "/geomagnetic/forecasts/get/three-day";

    public static ResponseEntity<ForecastResponse> exchangeDiurnal(TestRestTemplate restTemplate, int port) {
        return exchange(restTemplate, URI_GET_DIURNAL, port);
    }

    public static ResponseEntity<ForecastResponse> exchangeCurrent(TestRestTemplate restTemplate, int port) {
        return exchange(restTemplate, URI_GET_CURRENT, port);
    }

    public static ResponseEntity<ForecastResponse> exchangeThreeDay(TestRestTemplate restTemplate, int port) {
        return exchange(restTemplate, URI_GET_THREE_DAY, port);
    }

    private static ResponseEntity<ForecastResponse> exchange(TestRestTemplate restTemplate, String path, int port) {
        var uri = GeneratorHelper.generateUri(path, port);
        return restTemplate.getForEntity(uri, ForecastResponse.class);
    }

}