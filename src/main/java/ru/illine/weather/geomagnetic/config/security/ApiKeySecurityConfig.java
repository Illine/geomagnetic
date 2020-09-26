package ru.illine.weather.geomagnetic.config.security;

import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import ru.illine.weather.geomagnetic.config.property.ApiKeySecurityProperties;

import java.util.Optional;

@EnableWebSecurity
@Configuration
@Slf4j(topic = "GEOMAGNETIC-SECURITY")
public class ApiKeySecurityConfig {

    private final ApiKeySecurityProperties properties;

    @Autowired
    ApiKeySecurityConfig(ApiKeySecurityProperties properties) {
        this.properties = properties;
    }

    @Bean
    ApiKeySecurityConfigureAdapter apiKeySecurityConfigureAdapter(ApiKeyAuthorizationFilter apiKeyAuthorizationFilter,
                                                                  AuthenticationEntryPoint defaultAuthenticationEntryPoint) {
        return new ApiKeySecurityConfigureAdapter(apiKeyAuthorizationFilter, defaultAuthenticationEntryPoint);
    }

    @Bean
    ApiKeyAuthorizationFilter apiKeyAuthorizationFilter() {
        var filter = new ApiKeyAuthorizationFilter(properties.getHeaderName());
        filter.setAuthenticationManager(authentication -> {
            if (!properties.isEnabled()) {
                LOGGER.debug("ApiKey Authorization is disabled! Do nothing. Return true.");
                authentication.setAuthenticated(true);
                return authentication;
            }

            var principal = Optional.ofNullable(authentication.getPrincipal()).map(Object::toString).orElse(null);
            if (properties.getAccessKeys().contains(principal)) {
                authentication.setAuthenticated(true);
                return authentication;
            } else {
                throw new BadCredentialsException("The API key was not the expected value.");
            }
        });
        return filter;
    }

    @Bean
    AuthenticationEntryPoint defaultAuthenticationEntryPoint(ObjectMapper objectMapper, Tracer tracer) {
        return new DefaultAuthenticationEntryPoint(objectMapper, tracer);
    }
}