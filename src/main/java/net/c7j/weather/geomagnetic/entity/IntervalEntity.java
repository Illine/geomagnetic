package net.c7j.weather.geomagnetic.entity;

import net.c7j.weather.geomagnetic.common.ForecastIntervalType;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Immutable
@Table(schema = "geomagnetic", name = "interval")
public class IntervalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forecastSeqGenerator")
    @SequenceGenerator(name = "forecastSeqGenerator", sequenceName = "forecast_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "value")
    private ForecastIntervalType value;

}
