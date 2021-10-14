package com.weather.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City implements Serializable {
    private int id;
    private String name;
    private Coord coord;
    private String country;
    private int population;
    private int timezone;
    private int sunrise;
    private int sunset;
}