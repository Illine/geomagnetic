package net.c7j.weather.geomagnetic.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.c7j.weather.geomagnetic.model.base.IndexType;
import net.c7j.weather.geomagnetic.util.JsonWriter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ForecastDto implements Comparable<ForecastDto> {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "value")
    private IndexType index;

    @JsonProperty(value = "time")
    private Long time;

    public ForecastDto(IndexType index, Long time) {
        this.index = index;
        this.time = time;
    }

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