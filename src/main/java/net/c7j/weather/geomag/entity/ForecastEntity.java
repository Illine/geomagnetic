package net.c7j.weather.geomag.entity;

import net.c7j.weather.geomag.common.ActiveType;
import net.c7j.weather.geomag.common.ForecastIntervalType;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(schema = "geomagnetic", name = "forecast")
@SQLDelete(sql = "UPDATE geomagnetic.forecast SET active = false WHERE id = ?")
@Where(clause = "active = true")
public class ForecastEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forecastSeqGenerator")
    @SequenceGenerator(name = "forecastSeqGenerator", sequenceName = "forecast_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "value", nullable = false, updatable = false)
    private String value;

    @OneToOne
    @JoinColumn(name = "interval", nullable = false, updatable = false)
    private ForecastIntervalType interval;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @Enumerated
    @Column(name = "active", nullable = false)
    private ActiveType active;

    @PrePersist
    private void onCreate() {
        this.created = LocalDateTime.now();
        this.active = ActiveType.ENABLED;
    }
}
