package com.example.bolaksepak.ui.eventdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bolaksepak.Match;
import com.example.bolaksepak.R;
import com.example.bolaksepak.ui.teaminfo.TeamInfoActivity;
import com.squareup.picasso.Picasso;

public class EventDetailActivity extends AppCompatActivity {
    private Match mMatch;
    private TextView mMatchDate;
    private ImageView mHomeLogo;
    private ImageView mAwayLogo;
    private TextView mHomeScore;
    private TextView mAwayScore;


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
        mMatchDate = (TextView) findViewById(R.id.match_date);
        mHomeLogo = (ImageView) findViewById(R.id.home_club_logo);
        mAwayLogo = (ImageView) findViewById(R.id.away_club_logo);
        mHomeScore = (TextView) findViewById(R.id.home_club_score);
        mAwayScore = (TextView) findViewById(R.id.away_club_score);
        mMatchDate.setText(m.date);
        mHomeScore.setText(String.valueOf(m.home_score));
        mAwayScore.setText(String.valueOf(m.away_score));

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
