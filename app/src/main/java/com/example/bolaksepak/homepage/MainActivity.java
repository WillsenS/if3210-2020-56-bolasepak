package com.example.bolaksepak.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bolaksepak.R;
import com.example.bolaksepak.eventdetail.EventDetailActivity;

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

    public void viewMatchDetail(View view) {
        Intent intent = new Intent(this, EventDetailActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        TextView home_name = (TextView) itemView.findViewById(R.id.home_club_name);
//        TextView away_name = (TextView) itemView.findViewById(R.id.away_club_name);
//        TextView home_score = (TextView) itemView.findViewById(R.id.home_club_score);
//        TextView away_score = (TextView) itemView.findViewById(R.id.away_club_score);
//        TextView match_date = (TextView) itemView.findViewById(R.id.match_date);
//        ImageView home_logo = (ImageView) itemView.findViewById(R.id.home_club_logo);
//        ImageView away_logo = (ImageView) itemView.findViewById(R.id.away_club_logo);
//        String home_name_str = home_name.getText().toString();
//        String away_name_str = home_name.getText().toString();
//        String home_score_str = home_name.getText().toString();
//        String away_score_str = home_name.getText().toString();
//        String match_date_str = home_name.getText().toString();
//        String home_name_str = home_name.getText().toString();
//        String home_name_str = home_name.getText().toString();
        startActivity(intent);
    }
}
