package net.c7j.weather.geomagnetic.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.dom4j.tree.AbstractEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "geomagnetic", name = "forecast")
@SQLDelete(sql = "UPDATE geomagnetic.forecast SET active = false WHERE id = ?")
@Where(clause = "active = true")
public class ForecastEntity extends AbstractEntity {

    private static final long serialVersionUID = -6095660675931508374L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forecastSeqGenerator")
    @SequenceGenerator(
            schema = "geomagnetic", name = "forecastSeqGenerator",
            sequenceName = "forecast_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "time_interval_id", nullable = false, updatable = false)
    private TimeIntervalEntity timeInterval;

}
