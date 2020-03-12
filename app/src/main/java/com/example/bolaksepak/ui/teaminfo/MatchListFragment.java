package com.example.bolaksepak.ui.teaminfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bolaksepak.Match;
import com.example.bolaksepak.MatchAdapter;
import com.example.bolaksepak.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchListFragment extends Fragment {

    RecyclerView MatchListView;
    ArrayList<Match> MatchList = new ArrayList<>();
    int[] clubImages = new int[2];
    MatchAdapter md;


    public MatchListFragment() {
        // Required empty public constructor
    }

    public static MatchListFragment newInstance(int[] images) {
        MatchListFragment fragment = new MatchListFragment();
        Bundle args = new Bundle();
        args.putInt("ClubImage1", images[0]);
        args.putInt("ClubImage2", images[1]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < MatchList.size(); i++) {
            MatchList.set(i, new Match("99 Maret 2020", "Club 1", 1, "Club 2", 2));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_match_list, container, false);
        MatchListView = (RecyclerView) rootView.findViewById(R.id.match_list_fragment);
        Bundle args = getArguments();
        int image1 = args.getInt("ClubImage1");
        int image2 = args.getInt("ClubImage2");
        clubImages[0] = image1;
        clubImages[1] = image2;
        this.md = new MatchAdapter(getContext(), MatchList, clubImages);
        MatchListView.setAdapter(this.md);
        MatchListView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }
}
