package ru.illine.weather.geomagnetic.config;

import lombok.Generated;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Generated
@Configuration
@Profile(value = "!integration-test & !mock-test")
@EnableSchedulerLock(defaultLockAtMostFor = "PT5M")
public class ScheduleConfig {

    @Bean
    public LockProvider lockProvider(@Qualifier(value = "dataSource") DataSource dataSource) {
        return new JdbcTemplateLockProvider(dataSource);
    }

}