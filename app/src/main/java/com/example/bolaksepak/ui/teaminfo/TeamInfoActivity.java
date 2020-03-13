package com.example.bolaksepak.ui.teaminfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.bolaksepak.Match;
import com.example.bolaksepak.R;
import com.example.bolaksepak.Team;
import com.example.bolaksepak.adapter.MatchAdapter;
import com.example.bolaksepak.api.matchschedule.MatchFetcherSingleton;
import com.example.bolaksepak.ui.eventdetail.EventDetailActivity;
import com.example.bolaksepak.utils.MatchTeamDataLoader;
import com.google.android.material.tabs.TabLayout;


public class TeamInfoActivity extends AppCompatActivity{
    private static final MatchTeamDataLoader mDataLoader = new MatchTeamDataLoader();

    private static final int SEBELUM = 1;
    private static final int SESUDAH = 0;
    int mSelected = 0;
    boolean mInitialRender = true;
    Team mTeam;
    private ImageView mHeaderLogo;
    private TextView mHeaderTeamName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void displayFragment() {

        Log.d("EVENT", "DISPLAYFRAG");
        MatchListFragment fragment;
        if (mSelected == SEBELUM) {
            fragment = MatchListFragment.newInstance(mTeam.id, SEBELUM);
        } else { // (mSelected == SESUDAH)
            fragment = MatchListFragment.newInstance(mTeam.id, SESUDAH);
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


    @Override
    protected void onStop() {
        super.onStop();
        RequestQueue queue = MatchFetcherSingleton.getInstance(this).getRequestQueue();
        if (queue != null) {
            queue.cancelAll(request -> true);
        }
    }



}
