package net.c7j.weather.geomagnetic.dao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.c7j.weather.geomagnetic.dao.base.IndexType;
import net.c7j.weather.geomagnetic.util.JsonWriter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ForecastDto implements Comparable<ForecastDto> {

    @JsonProperty(value = "value")
    private IndexType index;

    @JsonProperty(value = "time")
    private Long time;

    public ForecastDto(int index, Long time) {
        this.index = IndexType.indexOf(index);
        this.time = time;
    }

    @Override
    public int compareTo(@NonNull ForecastDto that) {
        return Long.compare(this.time, that.time);
    }

    @Override
    public String toString() {
        return JsonWriter.toStringAsJson(this);
    }
}