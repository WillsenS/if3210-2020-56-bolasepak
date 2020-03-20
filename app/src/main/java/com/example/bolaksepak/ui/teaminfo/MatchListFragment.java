package com.example.bolaksepak.ui.teaminfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bolaksepak.Match;
import com.example.bolaksepak.adapter.MatchAdapter;
import com.example.bolaksepak.R;
import com.example.bolaksepak.api.matchschedule.MatchFetcherSingleton;
import com.example.bolaksepak.ui.eventdetail.EventDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchListFragment extends Fragment implements MatchAdapter.OnMatchListener {


    private static final String mGetTeamByNameUrl = "http://134.209.97.218:5050/api/v1/json/1/searchteams.php?t=";
    private static final String TYPE = "TYPE";
    private static final String TEAM_ID = "TEAM_ID";
    private static final int SEBELUM = 1;
    private Context mContext;
    private ArrayList<Match> mMatch = new ArrayList<>();
    private String id;
    private int type;
    private MatchAdapter mMatchAdapter;


    public MatchListFragment() {
        // Required empty public constructor
    }

    static MatchListFragment newInstance(String strId, int matchHistoryType) {
        MatchListFragment fragment = new MatchListFragment();
        Bundle args = new Bundle();
        args.putString(TEAM_ID, strId);
        args.putInt(TYPE, matchHistoryType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mMatchAdapter = new MatchAdapter(mContext, mMatch, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        assert args != null;
        this.id = args.getString(TEAM_ID);
        this.type = args.getInt(TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match_list, container, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        RequestQueue queue = MatchFetcherSingleton.getInstance(mContext).getRequestQueue();
        if (queue !=null) {
            queue.cancelAll(request -> true);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mMatchListView = view.findViewById(R.id.match_list_fragment);
        if (type == SEBELUM) {
            getLastMatchList();
//            if (mContext != null) {
            mMatchListView.setAdapter(mMatchAdapter);
            mMatchListView.setLayoutManager(new LinearLayoutManager(mContext));
//            }
        } else {
            getNextMatchList();
//            if (mContext != null) {
            mMatchListView.setAdapter(mMatchAdapter);
            mMatchListView.setLayoutManager(new LinearLayoutManager(mContext));
//            }
        }
    }

    private void getLastMatchList() {
        mMatch.clear();
        String url = "http://134.209.97.218:5050/api/v1/json/1/eventslast.php?id=".concat(id);
        Log.d("getLastMatchList", url);
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
                                            mMatch.add(new Match());
                                            JSONObject match = matchJSONArray.getJSONObject(i);

                                            mMatch.get(i).home_name = match.getString("strHomeTeam");
                                            mMatch.get(i).home_id = match.getString("idHomeTeam");
                                            mMatch.get(i).away_id = match.getString("idAwayTeam");
                                            mMatch.get(i).away_name = match.getString("strAwayTeam");
                                            //Assign Match Date
                                            if (match.isNull("strDate")) {
                                                mMatch.get(i).date = "No Date Info";
                                            } else {
                                                mMatch.get(i).date = match.getString("strDate");
                                            }
                                            //Assign Match Score
                                            if (match.isNull("intHomeScore")) {
                                                mMatch.get(i).home_score = -1;
                                            } else {
                                                mMatch.get(i).home_score = match.getInt("intHomeScore");
                                            }
                                            if (match.isNull("intAwayScore")) {
                                                mMatch.get(i).away_score = -1;
                                            } else {
                                                mMatch.get(i).away_score = match.getInt("intAwayScore");
                                            }

                                            //Assign Match Shots
                                            if (match.isNull("intHomeShots")) {
                                                mMatch.get(i).home_shots = -1;
                                            } else {
                                                mMatch.get(i).home_shots = match.getInt("intHomeShots");
                                            }
                                            if (match.isNull("intAwayShots")) {
                                                mMatch.get(i).away_shots = -1;
                                            } else {
                                                mMatch.get(i).away_shots = match.getInt("intAwayShots");
                                            }

                                            //Assign Goal Details
                                            if (!match.isNull("strHomeGoalDetails")) {
                                                String[] goalDetails = match.getString("strHomeGoalDetails").split(";");
                                                mMatch.get(i).homeGoalDetails = new ArrayList<>(Arrays.asList(goalDetails));
                                            }
                                            if (!match.isNull("strAwayGoalDetails")) {
                                                String[] goalDetails = match.getString("strAwayGoalDetails").split(";");
                                                mMatch.get(i).awayGoalDetails = new ArrayList<>(Arrays.asList(goalDetails));
                                            }

                                            //Assign Team Badge
                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mMatch.get(i).home_name), "UTF-8"), i, 1, mMatch);
                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mMatch.get(i).away_name), "UTF-8"), i, 2, mMatch);

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

    private void getNextMatchList() {
        mMatch.clear();
        String url = "http://134.209.97.218:5050/api/v1/json/1/eventsnext.php?id=".concat(id);
        Log.d("getNextMatchList", url);
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
                                            mMatch.add(new Match());
                                            JSONObject match = matchJSONArray.getJSONObject(i);

                                            mMatch.get(i).home_name = match.getString("strHomeTeam");
                                            mMatch.get(i).home_id = match.getString("idHomeTeam");
                                            mMatch.get(i).away_id = match.getString("idAwayTeam");
                                            mMatch.get(i).away_name = match.getString("strAwayTeam");
                                            //Assign Match Date
                                            if (match.isNull("strDate")) {
                                                mMatch.get(i).date = "No Date Info";
                                            } else {
                                                mMatch.get(i).date = match.getString("strDate");
                                            }
                                            //Assign Match Score
                                            if (match.isNull("intHomeScore")) {
                                                mMatch.get(i).home_score = -1;
                                            } else {
                                                mMatch.get(i).home_score = match.getInt("intHomeScore");
                                            }
                                            if (match.isNull("intAwayScore")) {
                                                mMatch.get(i).away_score = -1;
                                            } else {
                                                mMatch.get(i).away_score = match.getInt("intAwayScore");
                                            }

                                            //Assign Match Shots
                                            if (match.isNull("intHomeShots")) {
                                                mMatch.get(i).home_shots = -1;
                                            } else {
                                                mMatch.get(i).home_shots = match.getInt("intHomeShots");
                                            }
                                            if (match.isNull("intAwayShots")) {
                                                mMatch.get(i).away_shots = -1;
                                            } else {
                                                mMatch.get(i).away_shots = match.getInt("intAwayShots");
                                            }

                                            //Assign Goal Details
                                            if (!match.isNull("strHomeGoalDetails")) {
                                                String[] goalDetails = match.getString("strHomeGoalDetails").split(";");
                                                mMatch.get(i).homeGoalDetails = new ArrayList<>(Arrays.asList(goalDetails));
                                            }
                                            if (!match.isNull("strAwayGoalDetails")) {
                                                String[] goalDetails = match.getString("strAwayGoalDetails").split(";");
                                                mMatch.get(i).awayGoalDetails = new ArrayList<>(Arrays.asList(goalDetails));
                                            }

                                            //Assign Team Badge
                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mMatch.get(i).home_name), "UTF-8"), i, 1, mMatch);
                                            setTeamBadge(mGetTeamByNameUrl + URLEncoder.encode((mMatch.get(i).away_name), "UTF-8"), i, 2, mMatch);

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

    private void setTeamBadge(String url, int idx, int teamType, ArrayList<Match> match) {
        Log.d("setTeamBadge", url);
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
        Log.d("OnMatchClicked", "OnMatchClick (Fragment): ");
        Intent intent = new Intent(mContext, EventDetailActivity.class);
        intent.putExtra("MATCH", mMatch.get(position));
        startActivity(intent);
    }
}
