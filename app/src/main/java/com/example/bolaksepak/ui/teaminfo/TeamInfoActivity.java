package com.example.bolaksepak.ui.teaminfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.bolaksepak.R;
import com.example.bolaksepak.ui.eventdetail.EventDetailActivity;
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

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
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

        if (fragmentTransaction != null) {
            fragmentTransaction.replace(R.id.match_fragment_container, fragment).addToBackStack(null).commit();
        }
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
        Intent intent = new Intent(this, EventDetailActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
