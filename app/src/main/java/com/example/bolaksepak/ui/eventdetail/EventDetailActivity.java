package com.example.bolaksepak.ui.eventdetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.bolaksepak.Match;
import com.example.bolaksepak.R;
import com.example.bolaksepak.Team;
import com.example.bolaksepak.adapter.GoalDetailAdapter;
import com.example.bolaksepak.api.weather.TeamDB;
import com.example.bolaksepak.api.weather.Teamname;
import com.example.bolaksepak.api.weather.WeatherAPI;
import com.example.bolaksepak.api.weather.WeatherAPI;
import com.example.bolaksepak.api.weather.teams;
import com.example.bolaksepak.api.weather.weather;
import com.example.bolaksepak.api.weather.jlist;
import com.example.bolaksepak.api.weather.weather;
import com.example.bolaksepak.api.weather.weatherDB;
import com.example.bolaksepak.ui.teaminfo.TeamInfoActivity;
import com.example.bolaksepak.utils.MatchTeamDataLoader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.bolaksepak.api.matchschedule.MatchFetcherSingleton;
import com.example.bolaksepak.ui.teaminfo.TeamInfoActivity;
import com.example.bolaksepak.utils.MatchTeamDataLoader;

public class EventDetailActivity extends AppCompatActivity {
    private static final int TEAM_DETAIL_RESULT_FLAG = 1;
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
        Log.d("Yang mana yang duluan", "onCreate: ");
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
        getCity(m.home_name);
    }


    public void getCity(String hometeam) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://134.209.97.218:5050/api/v1/json/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TeamDB teamDB = retrofit.create(TeamDB.class);

        Call<Teamname> call = teamDB.getTeam(hometeam);
        call.enqueue(new Callback<Teamname>() {
            @Override
            public void onResponse(Call<Teamname> call, Response<Teamname> response) {
                Teamname fg = response.body();
                teams t = fg.getTeams(0);
                String city = t.getStrStadiumLocation();
                //String[] part = city.split(",");
                //city = part[1];
                getWeather(city);
            }

            @Override
            public void onFailure(Call<Teamname> call, Throwable t) {
                mWeather.setText("Error");
            }
        });
    }


    public void getWeather(String city) {
        final String res= "";
        String API = "fc96c0a7669abfee97f8d1b30efca557";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherDB weatherdb = retrofit.create(weatherDB.class);

        Call<WeatherAPI> call = weatherdb.getWeather(city,API);
        call.enqueue(new Callback<WeatherAPI>() {
            @Override
            public void onResponse(Call<WeatherAPI> call, Response<WeatherAPI> response) {
                WeatherAPI fg = response.body();

                for (int i=0; i<fg.getList().size(); i++) {
                    jlist j = fg.getList(i);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime APIdate = LocalDateTime.parse(j.getDt_txt(),formatter);
                    LocalDateTime now = LocalDateTime.now();
                    if(now.isBefore(APIdate)) {
                        j = fg.getList(i-1);
                        weather g = j.getForecast(0);
                        String res = g.getMain();
                        mWeather.setText(res);
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherAPI> call, Throwable t) {
                mWeather.setText("No Weather Data");
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
        startActivityForResult(intent, TEAM_DETAIL_RESULT_FLAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestQueue queue = MatchFetcherSingleton.getInstance(this).getRequestQueue();
        if (queue != null) {
            queue.cancelAll(request -> true);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



}
