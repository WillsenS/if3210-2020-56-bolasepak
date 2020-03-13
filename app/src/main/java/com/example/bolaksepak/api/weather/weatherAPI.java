package com.example.bolaksepak.api.weather;

public class weatherAPI {
    private String cod;
    private float message;
    private float cnt;
    list list;
    private City CityObject;


    // Getter Methods

    public String getCod() {
        return cod;
    }

    public float getMessage() {
        return message;
    }

    public float getCnt() {
        return cnt;
    }

    public list getList() {
        return list;
    }

    public City getCity() {
        return CityObject;
    }

    // Setter Methods

    public void setCod( String cod ) {
        this.cod = cod;
    }

    public void setMessage( float message ) {
        this.message = message;
    }

    public void setCnt( float cnt ) {
        this.cnt = cnt;
    }

    public void setCity( City cityObject ) {
        this.CityObject = cityObject;
    }
}

class Clouds {
    private float all;


    // Getter Methods

    public float getAll() {
        return all;
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
}

class Sys {
    private String pod;
    // Getter Methods

    public String getPod() {
        return pod;
    }

    // Setter Methods
}

class City {
    private float id;
    private String name;
    private Coord CoordObject;
    private String country;
    private float population;
    private float timezone;
    private float sunrise;
    private float sunset;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coord getCoord() {
        return CoordObject;
    }

    public String getCountry() {
        return country;
    }

    public float getPopulation() {
        return population;
    }

    public float getTimezone() {
        return timezone;
    }

    public float getSunrise() {
        return sunrise;
    }

    public float getSunset() {
        return sunset;
    }

    // Setter Methods

    public void setId( float id ) {
        this.id = id;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setCoord( Coord coordObject ) {
        this.CoordObject = coordObject;
    }

    public void setCountry( String country ) {
        this.country = country;
    }

    public void setPopulation( float population ) {
        this.population = population;
    }

    public void setTimezone( float timezone ) {
        this.timezone = timezone;
    }

    public void setSunrise( float sunrise ) {
        this.sunrise = sunrise;
    }

    public void setSunset( float sunset ) {
        this.sunset = sunset;
    }
}
class Coord {
    private float lat;
    private float lon;


    // Getter Methods

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    // Setter Methods

    public void setLat( float lat ) {
        this.lat = lat;
    }

    public void setLon( float lon ) {
        this.lon = lon;
    }
}