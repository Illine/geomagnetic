package net.c7j.weather.geomagnetic.dao.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.util.EnumSet;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum IndexType {

    NONE(0),
    LOW(1),
    QUITE(2),
    UNSETTLED(3),
    ACTIVE(4),
    MINOR_STORM(5),
    MODERATE_STORM(6),
    STRONG_STORM(7),
    SEVERE_STORM(8),
    EXTREME_STORM(9);

    @JsonValue
    private final Integer index;

    @JsonCreator
    public static IndexType indexOf(Integer index) {
        return EnumSet.allOf(IndexType.class)
                .stream()
                .filter(it -> Objects.equals(it.index, index))
                .findFirst()
                .orElseThrow();
    }

    public static IndexType indexOf(String index) {
        Assert.hasText(index, "The 'index' shouldn have a text!");
        return indexOf(Integer.valueOf(index));
    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }
}