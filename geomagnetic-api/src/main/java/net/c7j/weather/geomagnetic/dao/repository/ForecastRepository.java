package net.c7j.weather.geomagnetic.dao.repository;

import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

public interface ForecastRepository extends JpaRepository<ForecastEntity, Long> {

    Stream<ForecastEntity> findAllByForecastDateBetween(LocalDate start, LocalDate end);

    Stream<ForecastEntity> findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(LocalDate start, LocalDate end, LocalTime after);

}