package ru.illine.weather.geomagnetic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
class AsyncConfig {

    private static final int DEFAULT_CONSTANT_MAX_POOL_SIZE = 3;

    @Bean
    Executor forecastEventThreadPool() {
        var atp = new ThreadPoolTaskExecutor();
        atp.setMaxPoolSize(DEFAULT_CONSTANT_MAX_POOL_SIZE);
        atp.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return atp;
    }
}