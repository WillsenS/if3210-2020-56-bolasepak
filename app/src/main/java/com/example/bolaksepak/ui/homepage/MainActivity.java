package com.example.bolaksepak.ui.homepage;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bolaksepak.Match;
import com.example.bolaksepak.adapter.MatchAdapter;
import com.example.bolaksepak.R;
import com.example.bolaksepak.api.matchschedule.MatchFetcherSingleton;
import com.example.bolaksepak.ui.eventdetail.EventDetailActivity;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private MatchAdapter mMatchAdapter = new MatchAdapter(this, mMatchList, mClubImages, this);
    private ProgressBar pb;
    private String mTeamSearch = "";
    private TextInputEditText inputSearch;

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
        //Fetch data from TheSportDB
        getMatchList();

        //Show result
        generateRecyclerViewMatchList();
        //Hide Loader
//        findViewById(R.id.homepage_progressbar).setVisibility(View.GONE);
        //Toast.makeText(MainActivity.this, "first load", Toast.LENGTH_SHORT).show();

        inputSearch = findViewById(R.id.input);
        inputSearch.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMatchAdapter.getFilter().filter(s);
                //mTeamSearch = s.toString();
                //getMatchList();
                generateRecyclerViewMatchList();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //do nothing
            }
        });

    }

    public void generateRecyclerViewMatchList() {
        mMatchListView.setAdapter(mMatchAdapter);
        mMatchListView.setLayoutManager(new LinearLayoutManager(this));

    }

//    public void viewMatchDetail(View view) {
//        Intent intent = new Intent(this, EventDetailActivity.class);
//        startActivity(intent);
//    }

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
                                        Log.i("mNumOfMatches", String.valueOf(mNumOfMatches));
                                        Log.i("length of mMatchList array", String.valueOf(mMatchList.size()));
                                        for (int i = 0; i < matchJSONArray.length(); i++) {
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
                                                mMatchList.get(i + mNumOfMatches).homeGoalDetails = goalDetails;
                                                Log.d("HomeGoalDetails", "onResponse: " + Arrays.toString(goalDetails));
                                            }
                                            if (!match.isNull("strAwayGoalDetails")) {
                                                String[] goalDetails = match.getString("strAwayGoalDetails").split(";");
                                                mMatchList.get(i + mNumOfMatches).awayGoalDetails = goalDetails;
                                                Log.d("AwayGoalDetails", "onResponse: " + Arrays.toString(goalDetails));
                                            }

                                            //Assign Team Badge
                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mMatchList.get(i + mNumOfMatches).home_name), "UTF-8"), i + mNumOfMatches, 1);
                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mMatchList.get(i + mNumOfMatches).away_name), "UTF-8"), i + mNumOfMatches, 2);
                                            Log.d("Match index: ", String.valueOf(i + mNumOfMatches + 1));

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
        Log.d("Click Position", "OnMatchClick: " + position);
        Intent intent = new Intent(this, EventDetailActivity.class);
        intent.putExtra("MATCH", mMatchAdapter.mDisplayedMatchList.get(position));
        startActivity(intent);
    }
}


