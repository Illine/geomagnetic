package net.c7j.weather.geomagnetic.dao.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.c7j.weather.geomagnetic.util.JsonWriter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class BaseResponse {

    protected boolean success = true;
    protected String message = "Success";

    @Override
    public String toString() {
        return JsonWriter.toStringAsJson(this);
    }
}