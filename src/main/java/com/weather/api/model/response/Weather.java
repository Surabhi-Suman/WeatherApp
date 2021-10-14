package com.weather.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather implements Serializable {
    private int id;
    private String main;
    private String description;
    private String icon;
}