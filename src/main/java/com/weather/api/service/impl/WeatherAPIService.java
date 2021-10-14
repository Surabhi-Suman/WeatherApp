package com.weather.api.service.impl;

import com.weather.api.model.response.WeatherAPIResponse;

public interface WeatherAPIService {

    WeatherAPIResponse getWeatherForecast(Long zipCode);
    WeatherAPIResponse getWeatherForecastForNextFewDays(String cityName);
}
