package com.example.bolaksepak.ui.homepage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bolaksepak.Match;
import com.example.bolaksepak.R;
import com.example.bolaksepak.adapter.MatchAdapter;
import com.example.bolaksepak.api.matchschedule.MatchFetcherSingleton;
import com.example.bolaksepak.ui.eventdetail.EventDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements MatchAdapter.OnMatchListener {
    private static final String mGetTeamByNameUrl = "https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=";
    private static final String mGetLast5MatchByTeamId = "https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id=";
    private static final String mGetNext5MatchByTeamId = "https://www.thesportsdb.com/api/v1/json/1/eventsnext.php?id=";
    private Context mContext;
    private Activity mActivity;
    private RecyclerView mMatchListView;
    private ArrayList<Match> mMatchList = new ArrayList<>();
    private int[] mClubImages;
    private int mNumOfMatches = 0;
    private MatchAdapter mMatchAdapter = new MatchAdapter(this, mMatchList, this);
    private ProgressBar pb;
    private String mTeamSearch = "Barcelona";
    private static final int TEAM_DEAIL_RESULT_FLAG = 1;
    private TextView tv_steps;
    private Intent serviceIntent;
    private StepServiceReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        // Get the application context
        mContext = getApplicationContext();
        mActivity = MainActivity.this;

        //Show loader
//        findViewById(R.id.homepage_progressbar).setVisibility(View.VISIBLE);
        //Set Layout
        mMatchListView = (RecyclerView) findViewById(R.id.homepage_matchlist);
        tv_steps = findViewById(R.id.tv_steps);
        //Fetch data from TheSportDB
        getMatchList();

        //Show result
        generateRecyclerViewMatchList();
        //Hide Loader
//        findViewById(R.id.homepage_progressbar).setVisibility(View.GONE);

        serviceIntent = new Intent(MainActivity.this, com.example.bolaksepak.service.StepCountService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Register BroadcastReceiver
        //to receive event from our service
        receiver = new StepServiceReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("UpdateSensorValues");
        registerReceiver(receiver, intentFilter);
    }

    public void generateRecyclerViewMatchList() {
        mMatchListView.setAdapter(mMatchAdapter);
        mMatchListView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void getMatchList() {
        String getTeamIdByNameUrl = mGetTeamByNameUrl.concat(mTeamSearch);

//        mMatchList.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, getTeamIdByNameUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONArray teamsJSONArray = response.getJSONArray("teams");
                                for (int i = 0; i < teamsJSONArray.length(); i++) {
                                    String getLast5MatchByIdUrl = mGetLast5MatchByTeamId;
                                    String getNext5MatchByIdUrl = mGetNext5MatchByTeamId;
                                    JSONObject team = teamsJSONArray.getJSONObject(i);
                                    String teamId = team.getString("idTeam");
                                    //For each team, get 5 next and last match
                                    getLast5MatchByIdUrl = getLast5MatchByIdUrl.concat(teamId);
                                    getNext5MatchByIdUrl = getNext5MatchByIdUrl.concat(teamId);

                                    //Get all team matches
                                    getMatches(getLast5MatchByIdUrl, 1);
                                    getMatches(getNext5MatchByIdUrl, 2);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        MatchFetcherSingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest);
    }

    private void getMatches(String url, int type) { //type = 1 -> prev, type = 2 => next
        //TODO: Pecah jadi 2 method, getLastMatch, sama getNextMatch biar bisa dipake ulang di TeamInfoActivity
        //TODO: Bikin Interface MatchFetcher
        String apiKey;
        if (type == 1) {
            apiKey = "results";
        } else {
            apiKey = "events";
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                if (!response.isNull(apiKey)) {
                                    JSONArray matchJSONArray = response.getJSONArray(apiKey);
                                    if (matchJSONArray.length() > 0) {
                                        for (int i = 0; i < matchJSONArray.length(); i++) {
                                            //TODO: Rubah jadi while, kalo val dari key strSport nya bukan Soccer gausah ditambah ke list
                                            mMatchList.add(new Match());
                                            JSONObject match = matchJSONArray.getJSONObject(i);
                                            //Assign data to match object
                                            //TODO: Semua ini dan validasinya pindahin ke kelasnya aja biar lebih clean

                                            mMatchList.get(i + mNumOfMatches).home_name = match.getString("strHomeTeam");
                                            mMatchList.get(i + mNumOfMatches).home_id = match.getString("idHomeTeam");
                                            mMatchList.get(i + mNumOfMatches).away_id = match.getString("idAwayTeam");
                                            mMatchList.get(i + mNumOfMatches).away_name = match.getString("strAwayTeam");
                                            //Assign Match Date
                                            if (match.isNull("strDate")) {
                                                mMatchList.get(i + mNumOfMatches).date = "No Date Info";
                                            } else {
                                                mMatchList.get(i + mNumOfMatches).date = match.getString("strDate");
                                            }
                                            //Assign Match Score
                                            if (match.isNull("intHomeScore")) {
                                                mMatchList.get(i + mNumOfMatches).home_score = -1;
                                            } else {
                                                mMatchList.get(i + mNumOfMatches).home_score = match.getInt("intHomeScore");
                                            }
                                            if (match.isNull("intAwayScore")) {
                                                mMatchList.get(i + mNumOfMatches).away_score = -1;
                                            } else {
                                                mMatchList.get(i + mNumOfMatches).away_score = match.getInt("intAwayScore");
                                            }

                                            //Assign Match Shots
                                            if (match.isNull("intHomeShots")) {
                                                mMatchList.get(i + mNumOfMatches).home_shots = -1;
                                            } else {
                                                mMatchList.get(i + mNumOfMatches).home_shots = match.getInt("intHomeShots");
                                            }
                                            if (match.isNull("intAwayShots")) {
                                                mMatchList.get(i + mNumOfMatches).away_shots = -1;
                                            } else {
                                                mMatchList.get(i + mNumOfMatches).away_shots = match.getInt("intAwayShots");
                                            }

                                            //Assign Goal Details
                                            if (!match.isNull("strHomeGoalDetails")) {
                                                String[] goalDetails = match.getString("strHomeGoalDetails").split(";");
                                                mMatchList.get(i + mNumOfMatches).homeGoalDetails = new ArrayList<>(Arrays.asList(goalDetails));
//                                                Log.d("HomeGoalDetails", mMatchList.get(i + mNumOfMatches).home_name+ " :" + Arrays.toString(goalDetails));
//                                                Log.d("arraylist", String.valueOf(mMatchList.get(i + mNumOfMatches).homeGoalDetails));
                                            }
                                            if (!match.isNull("strAwayGoalDetails")) {
                                                String[] goalDetails = match.getString("strAwayGoalDetails").split(";");
                                                mMatchList.get(i + mNumOfMatches).awayGoalDetails = new ArrayList<>(Arrays.asList(goalDetails));
//                                                Log.d("AwayGoalDetails", mMatchList.get(i + mNumOfMatches).away_name + " :" + Arrays.toString(goalDetails));
//                                                Log.d("arraylist", String.valueOf(mMatchList.get(i + mNumOfMatches).awayGoalDetails));
                                            }

                                            //Assign Team Badge
                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mMatchList.get(i + mNumOfMatches).home_name), "UTF-8"), i + mNumOfMatches, 1);
                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mMatchList.get(i + mNumOfMatches).away_name), "UTF-8"), i + mNumOfMatches, 2);

                                        }
                                        mNumOfMatches += matchJSONArray.length();
                                    }
                                }
                            } catch (JSONException | UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR FETCH URL: ", url);
                    }
                });

        MatchFetcherSingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest);
    }

    private void setTeamBadge(String url, int idx, int teamType) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONArray matchJSONArray = response.getJSONArray("teams");
                                JSONObject team = matchJSONArray.getJSONObject(0);
                                if (teamType == 1) {
                                    mMatchList.get(idx).home_logo_url = team.getString("strTeamBadge").concat("/preview");

                                } else {
                                    mMatchList.get(idx).away_logo_url = team.getString("strTeamBadge").concat("/preview");
                                }
                                mMatchAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR FETCH URL: ", url);
                    }
                });
        mMatchAdapter.notifyDataSetChanged();
        MatchFetcherSingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void OnMatchClick(int position) {
        Intent intent = new Intent(this, EventDetailActivity.class);
        intent.putExtra("MATCH", mMatchList.get(position));
        startActivity(intent);

    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiver);
        super.onStop();
        RequestQueue queue = MatchFetcherSingleton.getInstance(this).getRequestQueue();
        if (queue != null) {
            queue.cancelAll(request -> true);
        }
    }


    private class StepServiceReceiver extends BroadcastReceiver {
        int sensorValues;
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            sensorValues = arg1.getIntExtra("Values", 0);
            String tv_step = sensorValues + " steps today...";
            tv_steps.setText(tv_step);
        }
    }
}


