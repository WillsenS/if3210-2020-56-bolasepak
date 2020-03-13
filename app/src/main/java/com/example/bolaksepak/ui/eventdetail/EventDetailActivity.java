package com.example.bolaksepak.ui.eventdetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolaksepak.Match;
import com.example.bolaksepak.R;
import com.example.bolaksepak.Team;
import com.example.bolaksepak.adapter.GoalDetailAdapter;
import com.example.bolaksepak.api.weather.list;
import com.example.bolaksepak.api.weather.weather;
import com.example.bolaksepak.api.weather.weatherAPI;
import com.example.bolaksepak.api.weather.weatherDB;
import com.example.bolaksepak.ui.teaminfo.TeamInfoActivity;
import com.example.bolaksepak.utils.MatchTeamDataLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventDetailActivity extends AppCompatActivity {
    private Match mMatch;
    private TextView mMatchDate;
    private ImageView mHomeLogo;
    private ImageView mAwayLogo;
    private TextView mHomeScore;
    private TextView mAwayScore;
    private TextView mHomeName;
    private TextView mAwayName;
    private TextView mHomeShots;
    private TextView mAwayShots;
    private TextView mWeather;
    private RecyclerView mHomeGoalDetails;
    private RecyclerView mAwayGoalDetails;
    private GoalDetailAdapter mHomeGoalAdapter;
    private GoalDetailAdapter mAwayGoalAdapter;
    private static final int HOME = 1;
    private static final int AWAY = 2;
    private static final MatchTeamDataLoader mDataLoader = new MatchTeamDataLoader();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Intent intent = getIntent();
        Match m = (Match) intent.getSerializableExtra("MATCH");
        assert m != null;
        this.mMatch = new Match(m);

//        Init layout element
        mMatchDate = findViewById(R.id.match_date);
        mHomeLogo = findViewById(R.id.home_club_logo);
        mAwayLogo = findViewById(R.id.away_club_logo);
        mHomeScore = findViewById(R.id.home_club_score);
        mAwayScore = findViewById(R.id.away_club_score);
        mHomeName = findViewById(R.id.home_club_name);
        mAwayName = findViewById(R.id.away_club_name);
        mHomeShots = findViewById(R.id.home_shots);
        mAwayShots = findViewById(R.id.away_shots);
        mHomeGoalDetails = findViewById(R.id.home_goal_detail);
        mAwayGoalDetails = findViewById(R.id.away_goal_detail);
        mWeather = findViewById(R.id.textView4);

        // Set the layout with values
        mMatchDate.setText(m.date);
        mHomeScore.setText(String.valueOf(m.home_score));
        mAwayScore.setText(String.valueOf(m.away_score));
        mDataLoader.validateAndSetNumberData(m.home_score, m.away_score, mHomeScore, mAwayScore);
        mDataLoader.validateAndSetNumberData(m.home_shots, m.away_shots, mHomeShots, mAwayShots);
        mHomeName.setText(m.home_name);
        mAwayName.setText(m.away_name);
        mDataLoader.loadImage(mMatch.home_logo_url, mHomeLogo);
        mDataLoader.loadImage(mMatch.away_logo_url, mAwayLogo);

        if (mMatch.homeGoalDetails != null) {
            mHomeGoalAdapter = new GoalDetailAdapter(this, mMatch.homeGoalDetails);
            mHomeGoalDetails.setAdapter(mHomeGoalAdapter);
            mHomeGoalDetails.setLayoutManager(new LinearLayoutManager(this));
        }

        if (mMatch.awayGoalDetails != null) {
            mAwayGoalAdapter = new GoalDetailAdapter(this, mMatch.awayGoalDetails);
            mAwayGoalDetails.setAdapter(mAwayGoalAdapter);
            mAwayGoalDetails.setLayoutManager(new LinearLayoutManager(this));
        }
        getWeather("London");

    }

    public void getWeather(String city) {
        final String res= "";
        String API = "fc96c0a7669abfee97f8d1b30efca557";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherDB weatherdb = retrofit.create(weatherDB.class);

        Call<List<weatherAPI>> call = weatherdb.getWeather(city,"fc96c0a7669abfee97f8d1b30efca557");
        call.enqueue(new Callback<List<weatherAPI>>() {
            @Override
            public void onResponse(Call<List<weatherAPI>> call, Response<List<weatherAPI>> response) {
                List<weatherAPI> forecast = response.body();

                for (weatherAPI w : forecast) {
                    list l = new list();
                    weather wet = new weather();
                    String res ="success";
                    list result;
                    result = w.getList();
                    l = result;
                    wet = l.getListWeather();
                    res += wet.getMain();

                    mWeather.setText(res);
                    break;
                }
            }

            @Override
            public void onFailure(Call<List<weatherAPI>> call, Throwable t) {
                mWeather.setText(t.getMessage());
            }

        });
    }

    public void viewHomeTeam(View view) {
        Intent intent = new Intent(this, TeamInfoActivity.class);
        intent.putExtra("TEAM_DATA", new Team(mMatch, HOME));
        startActivity(intent);
    }

    public void viewAwayTeam(View view) {
        Intent intent = new Intent(this, TeamInfoActivity.class);
        intent.putExtra("TEAM_DATA", new Team(mMatch, AWAY));
        startActivity(intent);
    }
}
