package ru.illine.weather.geomagnetic.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.illine.weather.geomagnetic.config.property.ApiKeySecurityProperties;
import ru.illine.weather.geomagnetic.model.dto.BaseResponse;
import ru.illine.weather.geomagnetic.test.helper.UriHelper;

import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.illine.weather.geomagnetic.test.helper.UriHelper.generateUri;

abstract class AbstractControllerTest {

    @LocalServerPort
    protected int port;

    @Autowired
    protected ApiKeySecurityProperties apiKeyProperties;

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

    protected <T extends BaseResponse> ResponseEntity<T> get(Class<T> response, HttpHeaders headers, String path) {
        var uri = generateUri(path, port);
        return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), response);
    }

    protected <T extends BaseResponse, R> ResponseEntity<T> patch(R request, HttpHeaders headers, String path) {
        return exchange(request, headers, HttpMethod.PATCH, path);
    }

    protected <T extends BaseResponse, R> ResponseEntity<T> exchange(R request, HttpHeaders headers, HttpMethod method, String path) {
        var uri = UriHelper.generateUri(path, port);
        return restTemplate.exchange(uri, method, new HttpEntity<>(request, headers), new ParameterizedTypeReference<>() {
        });
    }

    protected HttpHeaders buildDefaultHeaders() {
        var headers = new HttpHeaders();
        headers.set(apiKeyProperties.getHeaderName(), apiKeyProperties.getAccessKeys().stream().findAny().orElseThrow());
        return headers;
    }
}