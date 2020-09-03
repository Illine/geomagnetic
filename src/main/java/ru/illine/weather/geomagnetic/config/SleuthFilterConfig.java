package ru.illine.weather.geomagnetic.config;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
@Configuration
class SleuthFilterConfig implements Filter {

    private static final String TRACE_ID_HEADER_NAME = "X-B3-TraceId";
    private static final String SPAN_ID_HEADER_NAME = "X-B3-SpanId";

    private final Tracer tracer;

    @Autowired
    SleuthFilterConfig(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader(TRACE_ID_HEADER_NAME, tracer.currentSpan().context().traceIdString());
        httpServletResponse.setHeader(SPAN_ID_HEADER_NAME, tracer.currentSpan().context().spanIdString());
        chain.doFilter(request, response);
    }
}