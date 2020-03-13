package com.example.bolaksepak.api.weather;

import java.util.ArrayList;

public class list {
    private float dt;
    Main MainObject;
    weather weather;
    Clouds CloudsObject;
    Wind WindObject;
    Sys SysObject;
    private String dt_txt;


    // Getter Methods

    public float getDt() {
        return dt;
    }

    public Main getMain() {
        return MainObject;
    }

    public Clouds getClouds() {
        return CloudsObject;
    }

    public weather getListWeather() {return weather;}

    public Wind getWind() {
        return WindObject;
    }

    public Sys getSys() {
        return SysObject;
    }

    public String getDt_txt() {
        return dt_txt;
    }
}
