package net.c7j.weather.geomagnetic.event.listener;

import lombok.extern.slf4j.Slf4j;
import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.dao.dto.TxtForecastDto;
import net.c7j.weather.geomagnetic.mapper.impl.ForecastDtoMapper;
import net.c7j.weather.geomagnetic.mapper.impl.TxtForecastDtoMapper;
import net.c7j.weather.geomagnetic.service.ForecastUpsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Set;

@Component
@Slf4j(topic = "GEOMAGNETIC-EVENT")
public class ForecastListener {

    private final TxtForecastDtoMapper txtForecastMapper;
    private final ForecastDtoMapper forecastMapper;
    private final ForecastAccessService forecastAccessService;
    private final ForecastUpsertService forecastUpsertService;

    @Autowired
    ForecastListener(TxtForecastDtoMapper txtForecastMapper, ForecastDtoMapper forecastMapper,
                     ForecastAccessService forecastAccessService, ForecastUpsertService forecastUpsertService) {
        this.txtForecastMapper = txtForecastMapper;
        this.forecastMapper = forecastMapper;
        this.forecastAccessService = forecastAccessService;
        this.forecastUpsertService = forecastUpsertService;
    }

    @Transactional
    @EventListener
    @Async(value = "forecastEventThreadPool")
    public void onEvent(Set<TxtForecastDto> txtForecasts) {
        Assert.notNull(txtForecasts, "The 'txtForecasts' shouldn't be null!");
        LOGGER.info("A set of text forecast was listened");
        var upsertedForecast = forecastUpsertService.upsertForecasts(txtForecastMapper.convertToEntity(txtForecasts), LocalDate.now());
        forecastAccessService.save(forecastMapper.convertToDto(upsertedForecast));
    }
}