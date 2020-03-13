package com.example.bolaksepak.ui.eventdetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolaksepak.Match;
import com.example.bolaksepak.R;
import com.example.bolaksepak.adapter.GoalDetailAdapter;
import com.example.bolaksepak.ui.teaminfo.TeamInfoActivity;
import com.squareup.picasso.Picasso;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Intent intent = getIntent();
        Match m = (Match) intent.getSerializableExtra("MATCH");
        assert m != null;
        this.mMatch = new Match(m);
        Log.d("Match home = ", m.home_name);
        Log.d("Match away = ", m.away_name);

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
        if (m.home_score == -1 || m.away_score == -1) {
            mHomeScore.setText("-");
            mAwayScore.setText("-");
        } else {
            mHomeShots.setText(String.valueOf(m.home_score));
            mAwayShots.setText(String.valueOf(m.away_score));
        }
        if (m.home_shots == -1 || m.away_shots == -1) {
            mHomeShots.setText("-");
            mAwayShots.setText("-");
        } else {
            mHomeShots.setText(String.valueOf(m.home_shots));
            mAwayShots.setText(String.valueOf(m.away_shots));
        }
        mHomeName.setText(m.home_name);
        mAwayName.setText(m.away_name);

        if (!mMatch.home_logo_url.equals("")) {
            Picasso.get().load(mMatch.home_logo_url).
                    placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(mHomeLogo);
        } else {
            Picasso.get().load(R.drawable.placeholder)
                    .into(mHomeLogo);
        }

        if (!mMatch.away_logo_url.equals("")) {
            Picasso.get().load(mMatch.away_logo_url).
                    placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(mAwayLogo);
        } else {
            Picasso.get().load(R.drawable.placeholder)
                    .into(mAwayLogo);
        }
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
        startActivity(intent);
    }

    public void viewAwayTeam(View view) {
        Intent intent = new Intent(this, TeamInfoActivity.class);
        startActivity(intent);
    }
}
