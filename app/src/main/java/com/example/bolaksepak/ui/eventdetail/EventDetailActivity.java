package com.example.bolaksepak.ui.eventdetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.bolaksepak.Match;
import com.example.bolaksepak.R;
import com.example.bolaksepak.Team;
import com.example.bolaksepak.adapter.GoalDetailAdapter;
import com.example.bolaksepak.api.matchschedule.MatchFetcherSingleton;
import com.example.bolaksepak.ui.teaminfo.TeamInfoActivity;
import com.example.bolaksepak.utils.MatchTeamDataLoader;

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

    @Override
    protected void onStop() {
        super.onStop();
        RequestQueue queue = MatchFetcherSingleton.getInstance(this).getRequestQueue();
        if (queue != null) {
            queue.cancelAll(request -> true);
        }
    }
}
