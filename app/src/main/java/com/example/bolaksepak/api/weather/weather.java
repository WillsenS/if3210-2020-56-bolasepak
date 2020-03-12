package com.example.bolaksepak.api.weather;

import java.util.ArrayList;

public class weather {
    private Coord CoordObject;
    ArrayList<Object> weather = new ArrayList<Object>();
    private String base;
    private Main MainObject;
    private float visibility;
    private Wind WindObject;
    private Clouds CloudsObject;
    private float dt;
    private Sys SysObject;
    private float id;
    private String name;
    private float cod;


    // Getter Methods

    public Coord getCoord() {
        return CoordObject;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return MainObject;
    }

    public float getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return WindObject;
    }

    public Clouds getClouds() {
        return CloudsObject;
    }

    public float getDt() {
        return dt;
    }

    public Sys getSys() {
        return SysObject;
    }

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getCod() {
        return cod;
    }

}
public class Sys {
    private float type;
    private float id;
    private float message;
    private String country;
    private float sunrise;
    private float sunset;


    // Getter Methods

    public float getType() {
        return type;
    }

    public float getId() {
        return id;
    }

    public float getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public float getSunrise() {
        return sunrise;
    }

    public float getSunset() {
        return sunset;
    }

}
public class Clouds {
    private float all;


    // Getter Methods

    public float getAll() {
        return all;
    }

}
public class Wind {
    private float speed;
    private float deg;


    // Getter Methods

    public float getSpeed() {
        return speed;
    }

    public float getDeg() {
        return deg;
    }
}
public class Main {
    private float temp;
    private float pressure;
    private float humidity;
    private float temp_min;
    private float temp_max;


    // Getter Methods

    public float getTemp() {
        return temp;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

}
public class Coord {
    private float lon;
    private float lat;


    // Getter Methods

    public float getLon() {
        return lon;
    }

    public float getLat() {
        return lat;
    }

}
