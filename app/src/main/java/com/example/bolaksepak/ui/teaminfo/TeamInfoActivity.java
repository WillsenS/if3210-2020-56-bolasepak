package com.example.bolaksepak.ui.teaminfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bolaksepak.Match;
import com.example.bolaksepak.R;
import com.example.bolaksepak.Team;
import com.example.bolaksepak.adapter.MatchAdapter;
import com.example.bolaksepak.api.matchschedule.MatchFetcherSingleton;
import com.example.bolaksepak.utils.MatchTeamDataLoader;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

public class TeamInfoActivity extends AppCompatActivity {
    private static final MatchTeamDataLoader mDataLoader = new MatchTeamDataLoader();
    private static final String mGetTeamByNameUrl = "https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=";
    private static final String mGetLast5MatchByTeamId = "https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id=";
    private static final String mGetNext5MatchByTeamId = "https://www.thesportsdb.com/api/v1/json/1/eventsnext.php?id=";
    private static final int SEBELUM = 1;
    private static final int SESUDAH = 0;
    int mSelected = 0;
    boolean mInitialRender = true;
    Team mTeam;
    ArrayList<Match> mPastMatch = new ArrayList<>();
    ArrayList<Match> mNextMatch = new ArrayList<>();
    private ImageView mHeaderLogo;
    private TextView mHeaderTeamName;
    private Context mContext;
    private MatchAdapter mMatchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        //Get Team Data from intent
        Team team = (Team) getIntent().getSerializableExtra("TEAM_DATA");
        assert team != null;
        mTeam = new Team(team);
        //Set layout
        setContentView(R.layout.activity_team_info);

        //Find Element by ID
        mHeaderLogo = findViewById(R.id.header_logo);
        mHeaderTeamName = findViewById((R.id.header_team_name));

        //Put data into UI
        mDataLoader.loadImage(team.logo_url, mHeaderLogo);
        mHeaderTeamName.setText(team.name);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("EVENT", "ONTABSELECTED");
                int pos = tab.getPosition();
                switch (pos) {
                    case 0:
                        mSelected = 0;
                        break;
                    case 1:
                        mSelected = 1;
                        break;
                    default:
                        Log.d("Tab", "Gak ada yang ke select");
                        break;
                }
                displayFragment();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("EVENT", "ONTABUNSELECTED");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("EVENT", "ONTAB RE SELECTED");

            }

        });

        if (mInitialRender) {
            mInitialRender = false;
            displayFragment();
        }
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    public void displayFragment() {

        Log.d("EVENT", "DISPLAYFRAG");
        MatchListFragment fragment;
        if (mSelected == SEBELUM) { //TODO: Fungsi Fetch nya belom dipanggil
            getLastMatchList();
            mMatchAdapter = new MatchAdapter(mContext, mPastMatch, null);
            fragment = MatchListFragment.newInstance(mPastMatch);
        } else { // (mSelected == SESUDAH)
            getNextMatchList();
            mMatchAdapter = new MatchAdapter(mContext, mNextMatch, null);
            fragment = MatchListFragment.newInstance(mNextMatch);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.match_fragment_container, fragment).addToBackStack(null).commit();
    }

    public void closeFragment() {
        Log.d("EVENT", "DESTROYFRAG");
        // Get the FragmentManager.
        FragmentManager fragmentManager = getSupportFragmentManager();
        MatchListFragment simpleFragment = (MatchListFragment) fragmentManager
                .findFragmentById(R.id.match_fragment_container);
        if (simpleFragment != null) {
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
    }

    public void viewMatchDetail(View view) {
//        Intent intent = new Intent(this, EventDetailActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }



    public void getLastMatchList() {
        String url = "https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id=".concat(mTeam.id);
        Log.d("", "getLastMatchList: " + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                if (!response.isNull("results")) {
                                    JSONArray matchJSONArray = response.getJSONArray("results");
                                    if (matchJSONArray.length() > 0) {
                                        for (int i = 0; i < matchJSONArray.length(); i++) {
                                            mPastMatch.add(new Match());
                                            JSONObject match = matchJSONArray.getJSONObject(i);

                                            mPastMatch.get(i).home_name = match.getString("strHomeTeam");
                                            mPastMatch.get(i).home_id = match.getString("idHomeTeam");
                                            mPastMatch.get(i).away_id = match.getString("idAwayTeam");
                                            mPastMatch.get(i).away_name = match.getString("strAwayTeam");
                                            //Assign Match Date
                                            if (match.isNull("strDate")) {
                                                mPastMatch.get(i).date = "No Date Info";
                                            } else {
                                                mPastMatch.get(i).date = match.getString("strDate");
                                            }
                                            //Assign Match Score
                                            if (match.isNull("intHomeScore")) {
                                                mPastMatch.get(i).home_score = -1;
                                            } else {
                                                mPastMatch.get(i).home_score = match.getInt("intHomeScore");
                                            }
                                            if (match.isNull("intAwayScore")) {
                                                mPastMatch.get(i).away_score = -1;
                                            } else {
                                                mPastMatch.get(i).away_score = match.getInt("intAwayScore");
                                            }

                                            //Assign Match Shots
                                            if (match.isNull("intHomeShots")) {
                                                mPastMatch.get(i).home_shots = -1;
                                            } else {
                                                mPastMatch.get(i).home_shots = match.getInt("intHomeShots");
                                            }
                                            if (match.isNull("intAwayShots")) {
                                                mPastMatch.get(i).away_shots = -1;
                                            } else {
                                                mPastMatch.get(i).away_shots = match.getInt("intAwayShots");
                                            }

                                            //Assign Goal Details
                                            if (!match.isNull("strHomeGoalDetails")) {
                                                String[] goalDetails = match.getString("strHomeGoalDetails").split(";");
                                                mPastMatch.get(i).homeGoalDetails = new ArrayList<>(Arrays.asList(goalDetails));
                                            }
                                            if (!match.isNull("strAwayGoalDetails")) {
                                                String[] goalDetails = match.getString("strAwayGoalDetails").split(";");
                                                mPastMatch.get(i).awayGoalDetails = new ArrayList<>(Arrays.asList(goalDetails));
                                            }

                                            //Assign Team Badge
                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mPastMatch.get(i).home_name), "UTF-8"), i, 1, mPastMatch);
                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mPastMatch.get(i).away_name), "UTF-8"), i, 2, mPastMatch);

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

    public void getNextMatchList() {
        String url = "https://www.thesportsdb.com/api/v1/json/1/eventsnext.php?id=".concat(mTeam.id);
        Log.d("", "getLastMatchList: " + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                if (!response.isNull("events")) {
                                    JSONArray matchJSONArray = response.getJSONArray("events");
                                    if (matchJSONArray.length() > 0) {
                                        for (int i = 0; i < matchJSONArray.length(); i++) {
                                            mNextMatch.add(new Match());
                                            JSONObject match = matchJSONArray.getJSONObject(i);

                                            mNextMatch.get(i).home_name = match.getString("strHomeTeam");
                                            mNextMatch.get(i).home_id = match.getString("idHomeTeam");
                                            mNextMatch.get(i).away_id = match.getString("idAwayTeam");
                                            mNextMatch.get(i).away_name = match.getString("strAwayTeam");
                                            //Assign Match Date
                                            if (match.isNull("strDate")) {
                                                mNextMatch.get(i).date = "No Date Info";
                                            } else {
                                                mNextMatch.get(i).date = match.getString("strDate");
                                            }
                                            //Assign Match Score
                                            if (match.isNull("intHomeScore")) {
                                                mNextMatch.get(i).home_score = -1;
                                            } else {
                                                mNextMatch.get(i).home_score = match.getInt("intHomeScore");
                                            }
                                            if (match.isNull("intAwayScore")) {
                                                mNextMatch.get(i).away_score = -1;
                                            } else {
                                                mNextMatch.get(i).away_score = match.getInt("intAwayScore");
                                            }

                                            //Assign Match Shots
                                            if (match.isNull("intHomeShots")) {
                                                mNextMatch.get(i).home_shots = -1;
                                            } else {
                                                mNextMatch.get(i).home_shots = match.getInt("intHomeShots");
                                            }
                                            if (match.isNull("intAwayShots")) {
                                                mNextMatch.get(i).away_shots = -1;
                                            } else {
                                                mNextMatch.get(i).away_shots = match.getInt("intAwayShots");
                                            }

                                            //Assign Goal Details
                                            if (!match.isNull("strHomeGoalDetails")) {
                                                String[] goalDetails = match.getString("strHomeGoalDetails").split(";");
                                                mNextMatch.get(i).homeGoalDetails = new ArrayList<>(Arrays.asList(goalDetails));
                                            }
                                            if (!match.isNull("strAwayGoalDetails")) {
                                                String[] goalDetails = match.getString("strAwayGoalDetails").split(";");
                                                mNextMatch.get(i).awayGoalDetails = new ArrayList<>(Arrays.asList(goalDetails));
                                            }

                                            //Assign Team Badge
                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mNextMatch.get(i).home_name), "UTF-8"), i, 1, mNextMatch);
                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mNextMatch.get(i).away_name), "UTF-8"), i, 2, mNextMatch);

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

    public void setTeamBadge(String url, int idx, int teamType, ArrayList<Match> match) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONArray matchJSONArray = response.getJSONArray("teams");
                                JSONObject team = matchJSONArray.getJSONObject(0);
                                if (teamType == 1) {
                                    match.get(idx).home_logo_url = team.getString("strTeamBadge").concat("/preview");

                                } else {
                                    match.get(idx).away_logo_url = team.getString("strTeamBadge").concat("/preview");
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


}
