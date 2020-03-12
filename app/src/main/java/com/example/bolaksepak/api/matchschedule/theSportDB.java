package com.example.bolaksepak.api.matchschedule;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface theSportDB {
    @GET("eventslast.php?id={id1}")
    Call<List<LastEvent>> getLastEvent(@Path("id1") int lastId);

    @GET("eventsnext.php?id={id2}")
    Call<List<NextEvent>> getNextEvent(@Path("id2") int nextId);

    @GET("lookupteam.php?id={id3}")
    Call<List<SearchById>> getsearchById(@Path("id3") int searchId);

    @GET("searchteams.php?t={id4}")
    Call<List<SearchByName>> getsearchByName(@Path("id4") String name);
}
