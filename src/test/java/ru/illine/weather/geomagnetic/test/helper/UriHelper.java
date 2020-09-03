package ru.illine.weather.geomagnetic.test.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UriHelper {

    private static final String BASE_SCHEMA = "http";
    private static final String BASE_HOST = "localhost";

    public static String generateUri(String path, int port) {
        return UriComponentsBuilder.newInstance()
                .scheme(BASE_SCHEMA)
                .host(BASE_HOST)
                .port(port)
                .path(path)
                .build().toString();
    }
}