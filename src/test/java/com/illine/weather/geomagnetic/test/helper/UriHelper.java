package com.illine.weather.geomagnetic.test.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.function.BiFunction;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UriHelper {

    public static String generateUri(String path, int port) {
        return new TestUriBuilder().apply(path, port);
    }

    private static class TestUriBuilder implements BiFunction<String, Integer, String> {

        private static final String BASE_SCHEMA = "http";
        private static final String BASE_HOST = "localhost";

        @Override
        public String apply(String path, Integer port) {
            return UriComponentsBuilder.newInstance()
                    .scheme(BASE_SCHEMA)
                    .host(BASE_HOST)
                    .port(port)
                    .path(path)
                    .toUriString();
        }
    }
}