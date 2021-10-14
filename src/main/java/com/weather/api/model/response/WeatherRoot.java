package com.weather.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherRoot implements Serializable {
    private String cod;
    private int message;
    private int cnt;
    private List<ListData> list;
    private City city;
}