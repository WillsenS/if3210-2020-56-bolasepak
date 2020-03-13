package com.example.bolaksepak.api.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TeamDB {
    @GET("searchteams.php")
    Call<Teamname> getTeam(
            @Query("t") String t
    );
}
