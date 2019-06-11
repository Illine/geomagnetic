package net.c7j.weather.geomagnetic.dao.repository;

import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForecastRepository extends JpaRepository<ForecastEntity, Long> {
}
