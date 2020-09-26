package ru.illine.weather.geomagnetic.config.security;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ApiKeyAuthorizationFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final String NOT_AVAILABILITY = "N/A";

    private final String apiKeyHeaderName;

    public ApiKeyAuthorizationFilter(String apiKeyHeaderName) {
        this.apiKeyHeaderName = apiKeyHeaderName;
    }

    @Override
    protected String getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(apiKeyHeaderName)).orElse(NOT_AVAILABILITY);
    }

    @Override
    protected String getPreAuthenticatedCredentials(HttpServletRequest request) {
        return NOT_AVAILABILITY;
    }
}