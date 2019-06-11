package net.c7j.weather.geomagnetic.dao.entity;

import net.c7j.weather.geomagnetic.common.ActiveType;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -2903214314514916858L;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @Column(name = "modified", nullable = false)
    private LocalDateTime modified;

    @Enumerated
    @Column(name = "active", nullable = false)
    private ActiveType active;

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
        active = ActiveType.DELETED;
    }
}
