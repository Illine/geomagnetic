package net.c7j.weather.geomagnetic.test.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static net.c7j.weather.geomagnetic.test.helper.UriHelper.generateUri;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerHelper {

    public static <T> ResponseEntity<T> exchangeGet(TestRestTemplate restTemplate, Class<T> response, String path, int port) {
        var uri = generateUri(path, port);
        return restTemplate.getForEntity(uri, response);
    }

}