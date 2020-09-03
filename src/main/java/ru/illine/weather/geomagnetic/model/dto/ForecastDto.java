package ru.illine.weather.geomagnetic.model.dto;

import ru.illine.weather.geomagnetic.model.base.IndexType;
import lombok.Data;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ForecastDto implements Comparable<ForecastDto> {

    private Long id;

    private IndexType index;

    private LocalTime forecastTime;

    private LocalDate forecastDate;

    public ForecastDto updateIndex(IndexType index) {
        setIndex(index);
        return this;
    }

    @Override
    public int compareTo(ForecastDto that) {
        Assert.notNull(that, "The 'that' shouldn't be null!");
        var thisDateTime = LocalDateTime.of(this.forecastDate, this.forecastTime);
        var thatDateTime = LocalDateTime.of(that.forecastDate, that.forecastTime);
        return thisDateTime.compareTo(thatDateTime);
    }
}