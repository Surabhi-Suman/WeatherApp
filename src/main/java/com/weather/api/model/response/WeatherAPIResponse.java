package com.weather.api.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherAPIResponse implements Serializable {

    private String cityName;
    private String country;
    private long zipcode;
    private Map<String, Double> tomorrowsForecast;
}
