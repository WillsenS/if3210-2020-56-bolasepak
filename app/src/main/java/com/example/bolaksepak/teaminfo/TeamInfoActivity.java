package com.example.bolaksepak.teaminfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.bolaksepak.R;
import com.example.bolaksepak.eventdetail.EventDetailActivity;
import com.google.android.material.tabs.TabLayout;

public class TeamInfoActivity extends AppCompatActivity {
    int selected = 0;
    boolean initialRender = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                closeFragment();
                Log.d("EVENT", "ONTABSELECTED");
                int pos = tab.getPosition();
                switch (pos) {
                    case 0:
                        selected = 0;
                        break;
                    case 1:
                        selected = 1;
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
                closeFragment();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("EVENT", "ONTAB RE SELECTED");

            }

        });

        if (initialRender) {
            initialRender = false;
            displayFragment();
        }
    }

    public void displayFragment() {
        Log.d("EVENT", "DISPLAYFRAG");
        int[] clubImages;
        if (selected == 0) {
            clubImages = new int[]{R.drawable.club1, R.drawable.club2};
        } else if (selected == 1) {
            clubImages = new int[]{R.drawable.club3, R.drawable.club4};
        } else {
            clubImages = new int[]{R.drawable.club3, R.drawable.club3};
        }
        MatchListFragment fragment = MatchListFragment.newInstance(clubImages);
        FragmentManager fragmentManager = getSupportFragmentManager();


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.match_fragment_container, fragment).addToBackStack(null).commit();
    }

    public void closeFragment() {
        Log.d("EVENT", "DESTROYFRAG");
        // Get the FragmentManager.
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check to see if the fragment is already showing.
        MatchListFragment simpleFragment = (MatchListFragment) fragmentManager
                .findFragmentById(R.id.match_fragment_container);
        if (simpleFragment != null) {
            // Create and commit the transaction to remove the fragment.
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
    }

    public void viewMatchDetail(View view) {
        Intent intent = new Intent(this, EventDetailActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
