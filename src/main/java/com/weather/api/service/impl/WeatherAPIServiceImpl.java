package com.weather.api.service.impl;


import com.weather.api.exception.handler.WeatherForecastNotFoundException;
import com.weather.api.model.response.ListData;
import com.weather.api.model.response.WeatherAPIResponse;
import com.weather.api.model.response.WeatherRoot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherAPIServiceImpl implements WeatherAPIService {

    private final RestTemplate restTemplate;
    private final Environment environment;


    @Override
    public WeatherAPIResponse getWeatherForecast(Long zipcode) {

        log.info("*** Getting Weather forecast for Zipcode :: {}", zipcode);

        String apiUrl = environment.getProperty("weather.api.url");
        String key = environment.getProperty("weather.api.key");

        String api = String.format(apiUrl, zipcode, key);
        WeatherRoot response = restTemplate.getForObject(api, WeatherRoot.class);
        log.info("*** Weather forecast for provided Zipcode :: {}", response);

        if (Objects.isNull(response) || CollectionUtils.isEmpty(response.getList())) {
            throw new WeatherForecastNotFoundException("Weather forecast for tomorrow not found for provided zipcode");
        }

        WeatherAPIResponse weatherAPIResponse = WeatherAPIResponse.builder()
                .cityName(response.getCity().getName())
                .country(response.getCity().getCountry())
                .tomorrowsForecast(getTomorrowsPredictedData(response.getList()))
                .zipcode(zipcode)
                .build();
        log.info("*** Weather forecast for tomorrow :: {}", weatherAPIResponse);

        return weatherAPIResponse;
    }


    private Map<String, Double> getTomorrowsPredictedData(List<ListData> list) {
        log.info("*** Populating Weather forecast for tomorrow");

        Map<String, Double> hourlyTemperature = new HashMap<>();
        LocalDate nextThreeDaysLocalDate = LocalDate.now().plusDays(3);

        list.forEach(listData -> {
                    LocalDate forecastedDate = Instant.ofEpochMilli(new Date(listData.getDt() * 1000)
                            .getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                    if (nextThreeDaysLocalDate.getDayOfMonth() == forecastedDate.getDayOfMonth()) {
                        hourlyTemperature.put(listData.getDt_txt(), listData.getMain().getTemp_min());
                    }
                }
        );

        log.info("*** Populated Weather forecast for tomorrow");
        return hourlyTemperature;
    }



    @Override
    public WeatherAPIResponse getWeatherForecastForNextFewDays(String cityName) {

        log.info("*** Getting Weather forecast for City :: {}", cityName);

        String apiUrl = environment.getProperty("weather.api.url");
        String key = environment.getProperty("weather.api.key");

        String api = String.format(apiUrl, cityName, key);
        WeatherRoot response = restTemplate.getForObject(api, WeatherRoot.class);
        log.info("*** Weather forecast for provided City :: {}", response);

        if (Objects.isNull(response) || CollectionUtils.isEmpty(response.getList())) {
            throw new WeatherForecastNotFoundException("Weather forecast for tomorrow not found for provided City");
        }

        WeatherAPIResponse weatherAPIResponse = WeatherAPIResponse.builder()
                .cityName(response.getCity().getName())
                .country(response.getCity().getCountry())
                .tomorrowsForecast(getTomorrowsPredictedData(response.getList()))
                .build();
        log.info("*** Weather forecast for tomorrow :: {}", weatherAPIResponse);

        return weatherAPIResponse;
    }


}
