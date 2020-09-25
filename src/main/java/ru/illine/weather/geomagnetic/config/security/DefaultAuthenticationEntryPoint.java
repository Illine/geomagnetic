package ru.illine.weather.geomagnetic.config.security;

import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import ru.illine.weather.geomagnetic.model.dto.BaseResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String DEFAULT_TRACE_ID_HEADER_NAME = "X-B3-TraceId";
    private static final String DEFAULT_SPAN_ID_HEADER_NAME = "X-B3-SpanId";

    private final ObjectMapper objectMapper;
    private final Tracer tracer;

    DefaultAuthenticationEntryPoint(ObjectMapper objectMapper, Tracer tracer) {
        this.objectMapper = objectMapper;
        this.tracer = tracer;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setHeader(DEFAULT_TRACE_ID_HEADER_NAME, tracer.currentSpan().context().traceIdString());
        response.setHeader(DEFAULT_SPAN_ID_HEADER_NAME, tracer.currentSpan().context().spanIdString());
        response.getWriter().write(objectMapper.writeValueAsString(new BaseResponse(exception.getMessage())));
    }
}