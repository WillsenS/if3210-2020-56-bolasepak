package com.example.bolaksepak.ui.eventdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bolaksepak.R;
import com.example.bolaksepak.ui.teaminfo.TeamInfoActivity;

public class EventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
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
