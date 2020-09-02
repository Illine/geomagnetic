package ru.illine.weather.geomagnetic.rest.controller;

import ru.illine.weather.geomagnetic.model.dto.BaseResponse;
import ru.illine.weather.geomagnetic.test.helper.UriHelper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.BiConsumer;

import static ru.illine.weather.geomagnetic.test.helper.UriHelper.generateUri;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract class AbstractControllerTest {

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    protected BiConsumer<ResponseEntity<? extends BaseResponse>, HttpStatus> assertCall() {
        return (response, status) -> {
            assertNotNull(response);
            assertEquals(status, response.getStatusCode());
            assertNotNull(response.getBody());
        };
    }

    protected <T> ResponseEntity<T> get(Class<T> response, String path) {
        var uri = generateUri(path, port);
        return restTemplate.getForEntity(uri, response);
    }

    protected <T extends BaseResponse, R> ResponseEntity<T> patch(R request, String path) {
        return exchange(request, HttpMethod.PATCH, path);
    }

    protected <T extends BaseResponse, R> ResponseEntity<T> exchange(R request, HttpMethod method, String path) {
        var uri = UriHelper.generateUri(path, port);
        return restTemplate.exchange(uri, method, new HttpEntity<>(request), new ParameterizedTypeReference<>() {
        });
    }
}