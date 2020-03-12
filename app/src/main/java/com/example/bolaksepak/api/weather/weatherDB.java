package com.example.bolaksepak.api.weather;

import android.widget.DatePicker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface weatherDB {
    //api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}
    //api key: fc96c0a7669abfee97f8d1b30efca557

    @GET("weather")
    Call<List<weather>> getWeather(
            @Query("q") String city,
            @Query("appid") String api
    );
}
