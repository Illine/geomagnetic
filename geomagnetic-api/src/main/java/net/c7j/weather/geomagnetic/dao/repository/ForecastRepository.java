package net.c7j.weather.geomagnetic.dao.repository;

import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public interface ForecastRepository extends JpaRepository<ForecastEntity, Long> {

    Set<ForecastEntity> findAllByForecastDateBetween(LocalDate start, LocalDate end);

    Set<ForecastEntity> findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(LocalDate start, LocalDate end, LocalTime after);

}