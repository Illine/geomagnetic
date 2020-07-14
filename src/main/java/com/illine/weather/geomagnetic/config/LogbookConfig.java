package com.illine.weather.geomagnetic.config;

import brave.Tracer;
import com.illine.weather.geomagnetic.config.property.LogbookProperties;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.*;

import static com.illine.weather.geomagnetic.util.LogbackHelper.getLogger;

@Configuration
public class LogbookConfig {

    @Bean
    HttpLogWriter writer(LogbookProperties properties) {
        return new GeomagneticHttpLogWriter(properties);
    }

    @Bean
    public CorrelationId correlationId(Tracer tracer) {
        return new SleuthCorrelationId(tracer);
    }

    public static class GeomagneticHttpLogWriter implements HttpLogWriter {

        private final Logger logger;

        public GeomagneticHttpLogWriter(LogbookProperties properties) {
            logger = getLogger(properties.getName());
        }

        @Override
        public boolean isActive() {
            return logger.isInfoEnabled();
        }

        @Override
        public void write(Precorrelation precorrelation, String request) {
            logger.info(request);
        }

        @Override
        public void write(Correlation correlation, String response) {
            logger.info(response);
        }
    }

    public static class SleuthCorrelationId implements CorrelationId {

        private final Tracer tracer;

        public SleuthCorrelationId(Tracer tracer) {
            this.tracer = tracer;
        }

        @Override
        public String generate(HttpRequest request) {
            return tracer.currentSpan().context().traceIdString();
        }
    }
}