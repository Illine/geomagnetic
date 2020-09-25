package ru.illine.weather.geomagnetic.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

public class ApiKeySecurityConfigureAdapter extends WebSecurityConfigurerAdapter {

    private final ApiKeyAuthorizationFilter apiKeyAuthorizationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    ApiKeySecurityConfigureAdapter(ApiKeyAuthorizationFilter apiKeyAuthorizationFilter,
                                   AuthenticationEntryPoint authenticationEntryPoint) {
        this.apiKeyAuthorizationFilter = apiKeyAuthorizationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers(
                        "/forecasts/**",
                        "/services/**"
                ).authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilter(apiKeyAuthorizationFilter)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    }
}