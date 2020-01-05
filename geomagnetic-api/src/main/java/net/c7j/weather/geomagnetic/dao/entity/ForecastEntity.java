package net.c7j.weather.geomagnetic.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.c7j.weather.geomagnetic.model.base.ActiveType;
import net.c7j.weather.geomagnetic.model.base.IndexType;
import net.c7j.weather.geomagnetic.util.JsonWriter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "forecastTime", "forecastDate", "index", "active"})
@Entity
@Table(
        name = "forecasts",
        indexes = {
                @Index(name = "forecasts_time_inx", columnList = "forecast_time"),
                @Index(name = "forecasts_date_inx", columnList = "forecast_date")
        }
)
@SQLDelete(sql = "UPDATE forecasts SET active = false, updated = current_timestamp WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "active = true")
public class ForecastEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forecastsSeqGenerator")
    @SequenceGenerator(name = "forecastsSeqGenerator", sequenceName = "forecasts_seq")
    private Long id;

    @Column(name = "index", nullable = false)
    private IndexType index;

    @Column(name = "forecast_time", nullable = false, updatable = false)
    private LocalTime forecastTime;

    @Column(name = "forecast_date", nullable = false, updatable = false)
    private LocalDate forecastDate;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    @Column(name = "active", nullable = false)
    private ActiveType active;

    @Override
    public String toString() {
        return JsonWriter.toStringAsJson(this);
    }

    @PrePersist
    private void onCreate() {
        LocalDateTime current = LocalDateTime.now();
        created = current;
        updated = current;
        active = ActiveType.ENABLED;
    }

    @PreUpdate
    private void onUpdate() {
        updated = LocalDateTime.now();
    }

    @PreRemove
    private void onDelete() {
        updated = LocalDateTime.now();
        active = ActiveType.DISABLED;
    }
}