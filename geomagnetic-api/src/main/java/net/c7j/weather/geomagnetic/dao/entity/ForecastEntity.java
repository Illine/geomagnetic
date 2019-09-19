package net.c7j.weather.geomagnetic.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.c7j.weather.geomagnetic.model.base.ActiveType;
import net.c7j.weather.geomagnetic.model.base.IndexType;
import net.c7j.weather.geomagnetic.util.JsonWriter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
@Table(
        name = "forecast",
        indexes = {
                @Index(name = "forecast_time_inx", columnList = "forecast_time"),
                @Index(name = "forecast_date_inx", columnList = "forecast_date")
        }
)
@SQLDelete(sql = "UPDATE forecast SET active = false, modified = current_timestamp WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "active = true")
public class ForecastEntity implements Serializable {

    private static final long serialVersionUID = -6095660675931508374L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forecastSeqGenerator")
    @SequenceGenerator(name = "forecastSeqGenerator", sequenceName = "forecast_seq", allocationSize = 1)
    private Long id;

    @Column(name = "index", nullable = false)
    private IndexType index;

    @Column(name = "forecast_time", nullable = false, updatable = false)
    private LocalTime forecastTime;

    @Column(name = "forecast_date", nullable = false, updatable = false)
    private LocalDate forecastDate;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "active", nullable = false)
    private ActiveType active;

    public ForecastEntity(IndexType index, LocalTime forecastTime, LocalDate forecastDate) {
        this.index = index;
        this.forecastTime = forecastTime;
        this.forecastDate = forecastDate;
    }

    @Override
    public String toString() {
        return JsonWriter.toStringAsJson(this);
    }

    public ForecastEntity updateIndex(IndexType index) {
        setIndex(index);
        return this;
    }

    @PrePersist
    private void onCreate() {
        LocalDateTime current = LocalDateTime.now();
        created = current;
        modified = current;
        active = ActiveType.ENABLED;
    }

    @PreUpdate
    private void onUpdate() {
        modified = LocalDateTime.now();
    }

    @PreRemove
    private void onDelete() {
        modified = LocalDateTime.now();
        active = ActiveType.DELETED;
    }
}