package net.c7j.weather.geomagnetic.dao.entity;

import net.c7j.weather.geomagnetic.common.TimeIntervalType;

import org.dom4j.tree.AbstractEntity;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Immutable
@Table(
        schema = "geomagnetic", name = "time_interval",
        indexes = @Index(
                name = "unique_interval_value", columnList = "interval_value", unique = true
        )
)
@SQLDelete(sql = "UPDATE geomagnetic.time_interval SET active = false WHERE id = ?")
@Where(clause = "active = true")
public class TimeIntervalEntity extends AbstractEntity {

    private static final long serialVersionUID = 4830952070497310925L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timeIntervalSeq")
    @SequenceGenerator(
            name = "timeIntervalSeq", schema = "geomagnetic",
            sequenceName = "time_interval_seq", allocationSize = 1
    )
    private Long id;

    @Column(name = "interval_value")
    private TimeIntervalType intervalValue;

}
