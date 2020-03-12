package com.example.bolaksepak.ui.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bolaksepak.Match;
import com.example.bolaksepak.MatchAdapter;
import com.example.bolaksepak.R;
import com.example.bolaksepak.api.matchschedule.MatchFetcherSingleton;
import com.example.bolaksepak.ui.eventdetail.EventDetailActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private static final String mGetTeamByNameUrl = "https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=";
    private MatchAdapter mMatchAdapter;
    private Context mContext;
    private Activity mActivity;
    private RecyclerView mMatchListView;
    private Match[] mMatchList = new Match[10];
    private int[] mClubImages = {
            R.drawable.club1,
            R.drawable.club2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        // Get the application context
        mContext = getApplicationContext();
        mActivity = MainActivity.this;

        //Set Layout
        mMatchListView = (RecyclerView) findViewById(R.id.homepage_matchlist);

        //Fetch data from TheSportDB
        getMatchList();

        for (int i = 0; i < mMatchList.length; i++) {
            mMatchList[i] = new Match("99 Maret 2020", "Club 1", 1, "Club 2", 2);
        }
        this.mMatchAdapter = new MatchAdapter(this, mMatchList, mClubImages);
        mMatchListView.setAdapter(mMatchAdapter);
        mMatchListView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void viewMatchDetail(View view) {
        Intent intent = new Intent(this, EventDetailActivity.class);
        startActivity(intent);
    }

    private void getMatchList() {
        String getUrl = mGetTeamByNameUrl.concat("Arsenal");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, getUrl, null, response -> Log.d("Isi data", response.toString()), error -> {
                    // TODO: Handle error
                    Log.d("Error fetch", "lah cacad");
                });

        MatchFetcherSingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest);
    }
}
