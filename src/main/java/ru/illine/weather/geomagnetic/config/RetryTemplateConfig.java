package ru.illine.weather.geomagnetic.config;

import ru.illine.weather.geomagnetic.config.property.RestProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@RequiredArgsConstructor
class RetryTemplateConfig {

    @Bean
    public RetryTemplate etlRetryTemplate(RestProperties properties) {
        var retryTemplate = new RetryTemplate();
        var fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(properties.getRetry().getDelayInMs());
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        var retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(properties.getRetry().getMaxAttempts());
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }
}
