package com.example.bolaksepak.ui.eventdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.bolaksepak.Match;
import com.example.bolaksepak.R;
import com.example.bolaksepak.ui.teaminfo.TeamInfoActivity;

public class EventDetailActivity extends AppCompatActivity {
    private Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Intent intent = getIntent();
        Match m = (Match) intent.getSerializableExtra("MATCH");
        assert m != null;
        this.match = new Match(m);
        Log.d("Match home = ", m.home_name);
        Log.d("Match away = ", m.away_name);
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
