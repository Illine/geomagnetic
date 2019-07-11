package net.c7j.weather.geomagnetic.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static net.c7j.weather.geomagnetic.util.LocalTimeFormatter.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j(topic = "GEOMAGNETIC-UTIL")
public final class JsonWriter {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        var timeModule = configureTimeModule();
        configure(timeModule);
    }

    public static <T> String toStringAsJson(T object) {
        Assert.notNull(object, "The 'object' shouldn't be null!");
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error mapping to json! Return an 'unknown' value!", e);
            return "unknown";
        }
    }

    private static void configure(JavaTimeModule timeModule) {
        mapper.registerModule(timeModule);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private static JavaTimeModule configureTimeModule() {
        var module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DEFAULT_DATE_TIME_FORMATTER));
        module.addSerializer(LocalDate.class, new LocalDateSerializer(DEFAULT_DATE_FORMATTER));
        module.addSerializer(LocalTime.class, new LocalTimeSerializer(DEFAULT_TIME_FORMATTER));
        return module;
    }
}