package com.illine.weather.geomagnetic.config;

import com.illine.weather.geomagnetic.config.property.RestRetryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@RequiredArgsConstructor
class RetryTemplateConfig {

    private final RestRetryProperties properties;

    @Bean
    public RetryTemplate etlRetryTemplate() {
        var retryTemplate = new RetryTemplate();
        var fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(properties.getDelayInMs());
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        var retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(properties.getMaxAttempts());
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }
}
