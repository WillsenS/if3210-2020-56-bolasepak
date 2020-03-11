package com.example.bolaksepak.teaminfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bolaksepak.Match;
import com.example.bolaksepak.MatchAdapter;
import com.example.bolaksepak.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchListFragment extends Fragment {

    RecyclerView MatchListView;
    Match[] MatchList = new Match[10];
    int[] clubImages = {
            R.drawable.club1,
            R.drawable.club2
    };
    MatchAdapter md;

    public MatchListFragment() {
        // Required empty public constructor
    }

    public static MatchListFragment newInstance() {
        MatchListFragment fragment = new MatchListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < MatchList.length; i++) {
            MatchList[i] = new Match("99 Maret 2020", "Club 1", 1, "Club 2", 2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_match_list, container, false);
        MatchListView = (RecyclerView) rootView.findViewById(R.id.match_list_fragment);
        this.md = new MatchAdapter(getContext(), MatchList, clubImages);
        Log.d("this.md: ", String.valueOf(this.md));
        MatchListView.setAdapter(this.md);
        MatchListView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }
}
