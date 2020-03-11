package com.example.bolaksepak.homepage;

import android.os.Bundle;

import com.example.bolaksepak.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    RecyclerView MatchListView;
    Match[] MatchList = new Match[10];
    int[] clubImages = {
            R.drawable.club1,
            R.drawable.club2
    };
    MatchAdapter md;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        MatchListView = (RecyclerView) findViewById(R.id.matchlist);
        for (int i = 0; i < MatchList.length; i++) {
            MatchList[i] = new Match("99 Maret 2020", "Club 1", 1, "Club 2", 2);
        }

        this.md = new MatchAdapter(this, MatchList, clubImages);
        MatchListView.setAdapter(md);
        MatchListView.setLayoutManager(new LinearLayoutManager(this));
    }

}
