package com.example.bolaksepak.api.weather;

import java.util.ArrayList;
import java.util.List;

public class jlist {
    public jlist() {
        super();
    }
    private float dt;
    Main MainObject;
    List<weather> weather = new ArrayList<weather>(100);
    Clouds CloudsObject;
    Wind WindObject;
    Sys SysObject;
    private String dt_txt;


    // Getter Methods
    public weather getForecast(int w) {
        return weather.get(w);
    }
    public float getDt() {
        return dt;
    }

    public Main getMain() {
        return MainObject;
    }

    public Clouds getClouds() {
        return CloudsObject;
    }

    public Wind getWind() {
        return WindObject;
    }

    public Sys getSys() {
        return SysObject;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    // Setter Methods

    public void setForecast(List<weather> f) {
        this.weather = f;
    }

    public void setDt( float dt ) {
        this.dt = dt;
    }

    public void setMain( Main mainObject ) {
        this.MainObject = mainObject;
    }

    public void setClouds( Clouds cloudsObject ) {
        this.CloudsObject = cloudsObject;
    }

    public void setWind( Wind windObject ) {
        this.WindObject = windObject;
    }

    public void setSys( Sys sysObject ) {
        this.SysObject = sysObject;
    }

    public void setDt_txt( String dt_txt ) {
        this.dt_txt = dt_txt;
    }
}
class Sys {
    private String pod;


    // Getter Methods

    public String getPod() {
        return pod;
    }

    // Setter Methods

    public void setPod( String pod ) {
        this.pod = pod;
    }
}
class Wind {
    private float speed;
    private float deg;


    // Getter Methods

    public float getSpeed() {
        return speed;
    }

    public float getDeg() {
        return deg;
    }

    // Setter Methods

    public void setSpeed( float speed ) {
        this.speed = speed;
    }

    public void setDeg( float deg ) {
        this.deg = deg;
    }
}
class Clouds {
    private float all;


    // Getter Methods

    public float getAll() {
        return all;
    }

    // Setter Methods

    public void setAll( float all ) {
        this.all = all;
    }
}
class Main {
    private float temp;
    private float feels_like;
    private float temp_min;
    private float temp_max;
    private float pressure;
    private float sea_level;
    private float grnd_level;
    private float humidity;
    private float temp_kf;


    // Getter Methods

    public float getTemp() {
        return temp;
    }

    public float getFeels_like() {
        return feels_like;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public float getPressure() {
        return pressure;
    }

    public float getSea_level() {
        return sea_level;
    }

    public float getGrnd_level() {
        return grnd_level;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getTemp_kf() {
        return temp_kf;
    }

    // Setter Methods

    public void setTemp( float temp ) {
        this.temp = temp;
    }

    public void setFeels_like( float feels_like ) {
        this.feels_like = feels_like;
    }

    public void setTemp_min( float temp_min ) {
        this.temp_min = temp_min;
    }

    public void setTemp_max( float temp_max ) {
        this.temp_max = temp_max;
    }

    public void setPressure( float pressure ) {
        this.pressure = pressure;
    }

    public void setSea_level( float sea_level ) {
        this.sea_level = sea_level;
    }

    public void setGrnd_level( float grnd_level ) {
        this.grnd_level = grnd_level;
    }

    public void setHumidity( float humidity ) {
        this.humidity = humidity;
    }

    public void setTemp_kf( float temp_kf ) {
        this.temp_kf = temp_kf;
    }
}
