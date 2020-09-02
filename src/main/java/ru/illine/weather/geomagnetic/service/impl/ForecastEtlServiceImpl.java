package ru.illine.weather.geomagnetic.service.impl;

import ru.illine.weather.geomagnetic.client.SwpcNoaaClient;
import ru.illine.weather.geomagnetic.dao.access.ForecastAccessService;
import ru.illine.weather.geomagnetic.mapper.impl.TxtForecastDtoMapper;
import ru.illine.weather.geomagnetic.model.dto.ForecastDto;
import ru.illine.weather.geomagnetic.service.EtlService;
import ru.illine.weather.geomagnetic.service.ForecastParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "GEOMAGNETIC-SERVICE")
public class ForecastEtlServiceImpl implements EtlService {

    private final SwpcNoaaClient swpcNoaaClient;
    private final ForecastParserService forecastParserService;
    private final TxtForecastDtoMapper txtForecastMapper;
    private final ForecastAccessService forecastAccessService;

    @Autowired
    ForecastEtlServiceImpl(SwpcNoaaClient swpcNoaaClient,
                           ForecastParserService forecastParserService, TxtForecastDtoMapper txtForecastMapper,
                           ForecastAccessService forecastAccessService) {
        this.swpcNoaaClient = swpcNoaaClient;
        this.forecastParserService = forecastParserService;
        this.txtForecastMapper = txtForecastMapper;
        this.forecastAccessService = forecastAccessService;
    }

    @Override
    public void updateForecasts() {
        LOGGER.info("Update geomagnetic forecast is started");
        var forecasts = getForecasts();
        var updatedForecasts = updateForecasts(forecasts);
        forecastAccessService.update(updatedForecasts);
    }

    private List<ForecastDto> getForecasts() {
        var stringForecast = swpcNoaaClient.get3DayGeomagneticForecast();
        LOGGER.info("Get response from SWP NOAA: \n{}\n", stringForecast.getBody());
        var txtForecasts = forecastParserService.parse(stringForecast.getBody());
        return txtForecastMapper.convertToSources(txtForecasts);
    }

    private Set<ForecastDto> updateForecasts(List<ForecastDto> forecasts) {
        var dateTime2ThreeDayForecast =
                forecastAccessService.findThreeDays(LocalDate.now())
                        .stream()
                        .collect(Collectors.toMap(k -> LocalDateTime.of(k.getForecastDate(), k.getForecastTime()), Function.identity()));

        return forecasts.stream()
                .map(merge(dateTime2ThreeDayForecast))
                .collect(Collectors.toSet());
    }

    private Function<ForecastDto, ForecastDto> merge(Map<LocalDateTime, ForecastDto> dateTime2ThreeDayForecast) {
        return forecast ->
                dateTime2ThreeDayForecast.merge(
                        LocalDateTime.of(forecast.getForecastDate(), forecast.getForecastTime()),
                        forecast,
                        (oldForecast, newForecast) -> oldForecast.updateIndex(newForecast.getIndex())
                );
    }
}