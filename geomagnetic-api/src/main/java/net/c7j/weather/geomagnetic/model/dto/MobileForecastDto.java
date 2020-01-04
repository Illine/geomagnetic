package net.c7j.weather.geomagnetic.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.c7j.weather.geomagnetic.model.base.IndexType;
import net.c7j.weather.geomagnetic.util.JsonWriter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MobileForecastDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "value")
    private IndexType index;

    @JsonProperty(value = "time")
    private Long time;

    @Override
    public String toString() {
        return JsonWriter.toStringAsJson(this);
    }
}