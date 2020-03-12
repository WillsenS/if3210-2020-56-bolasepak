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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String mGetTeamByNameUrl = "https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=";
    private static final String mGetLast5MatchByTeamId = "https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id=";
    private static final String mGetNext5MatchByTeamId = "https://www.thesportsdb.com/api/v1/json/1/eventsnext.php?id=";
    private MatchAdapter mMatchAdapter;
    private Context mContext;
    private Activity mActivity;
    private RecyclerView mMatchListView;
    private ArrayList<Match> mMatchList = new ArrayList<>();
    private int[] mClubImages;
    private String teamSearch = "Arsenal";

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
        Log.d("value", mMatchList.toString());
        generateRecyclerViewMatchList();
//        this.mMatchAdapter = new MatchAdapter(this, mMatchList, mClubImages);
//        mMatchListView.setAdapter(mMatchAdapter);
//        mMatchListView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void generateRecyclerViewMatchList() {
        this.mMatchAdapter = new MatchAdapter(this, mMatchList, mClubImages);
        mMatchListView.setAdapter(mMatchAdapter);
        mMatchListView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void viewMatchDetail(View view) {
        Intent intent = new Intent(this, EventDetailActivity.class);
        startActivity(intent);
    }

    private void getMatchList() {
        String getTeamIdByNameUrl = mGetTeamByNameUrl.concat(teamSearch);


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
                                    getMatches(getLast5MatchByIdUrl, i, 1);
                                    getMatches(getNext5MatchByIdUrl, i, 2);
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

    private void getMatches(String url, int idx, int type) { //type = 1 -> prev, type = 2 => next
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
                                            JSONObject match = matchJSONArray.getJSONObject(i);
                                            //Assign data to match object
                                            mMatchList.add(new Match());
                                            mMatchList.get(idx).home_name = match.getString("strHomeTeam");
                                            mMatchList.get(idx).home_id = match.getString("idHomeTeam");
                                            mMatchList.get(idx).away_id = match.getString("idAwayTeam");
                                            mMatchList.get(idx).away_name = match.getString("strAwayTeam");
                                            if (match.isNull("strDate")) {
                                                mMatchList.get(idx).date = "TBA";
                                            } else {
                                                mMatchList.get(idx).date = match.getString("strDate");
                                            }
                                            if (match.isNull("intHomeScore")) {
                                                mMatchList.get(idx).home_score = 0;
                                            } else {
                                                mMatchList.get(idx).home_score = match.getInt("intHomeScore");
                                            }
                                            if (match.isNull("intAwayScore")) {
                                                mMatchList.get(idx).away_score = 0;
                                            } else {
                                                mMatchList.get(idx).away_score = match.getInt("intAwayScore");
                                            }
//                                            Log.d("home team: ", mMatchList.get(idx).home_name);
//                                            Log.d("away_team: ", mMatchList.get(idx).away_name);
//                                            Log.d("date: ", mMatchList.get(idx).date);
//                                            //TODO: handle null

                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mMatchList.get(idx).home_name), "UTF-8"), idx, type, 1);
                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mMatchList.get(idx).away_name), "UTF-8"), idx, type, 2);

                                        }
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

    private void setTeamBadge(String url, int idx, int eventType, int teamType) {
        Log.d("iscalled", url);
        String apiKey;
        if (eventType == 1) {
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
                                Log.d("resp", response.toString());
                                JSONArray matchJSONArray = response.getJSONArray("teams");
                                JSONObject team = matchJSONArray.getJSONObject(0);
                                if (teamType == 1) {
                                    mMatchList.get(idx).home_logo_url = team.getString("strTeamBadge").concat("/preview");

                                } else {
                                    mMatchList.get(idx).away_logo_url = team.getString("strTeamBadge").concat("/preview");
                                }
                                Log.d("home team: ", mMatchList.get(idx).home_name);
                                Log.d("away_team: ", mMatchList.get(idx).away_name);
                                Log.d("date: ", mMatchList.get(idx).date);
                                Log.d("home logo url", mMatchList.get(idx).home_logo_url);
                                Log.d("away logo url", mMatchList.get(idx).away_logo_url);
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

        MatchFetcherSingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest);

    }
}


