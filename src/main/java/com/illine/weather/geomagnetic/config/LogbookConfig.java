package com.illine.weather.geomagnetic.config;

import com.illine.weather.geomagnetic.config.property.LogbookProperties;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

import static com.illine.weather.geomagnetic.util.LogbackHelper.getLogger;

@Configuration
public class LogbookConfig {

    private final LogbookProperties properties;

    @Autowired
    LogbookConfig(LogbookProperties properties) {
        this.properties = properties;
    }

    @Bean
    HttpLogWriter writer() {
        return new GeomagneticHttpLogWriter(properties);
    }

    public static class GeomagneticHttpLogWriter implements HttpLogWriter {

        private final Logger logger;

        public GeomagneticHttpLogWriter(LogbookProperties properties) {
            logger = getLogger(properties.getName(), properties.getLevel());
        }

        @Override
        public boolean isActive() {
            return logger.isDebugEnabled() || logger.isInfoEnabled() || logger.isWarnEnabled();
        }

        @Override
        public void write(Precorrelation precorrelation, String request) {
            log(request);
        }

        @Override
        public void write(Correlation correlation, String response) {
            log(response);
        }

        private void log(String message) {
            if (logger.isDebugEnabled()) {
                logger.debug(message);
            } else if (logger.isInfoEnabled()) {
                logger.info(message);
            } else if (logger.isWarnEnabled()) {
                logger.warn(message);
            }
        }
    }
}