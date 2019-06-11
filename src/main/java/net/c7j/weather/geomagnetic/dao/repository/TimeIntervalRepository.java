package net.c7j.weather.geomagnetic.dao.repository;

import net.c7j.weather.geomagnetic.dao.entity.TimeIntervalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeIntervalRepository extends JpaRepository<TimeIntervalEntity, Long> {
}
