package com.example.bolaksepak.ui.teaminfo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bolaksepak.Match;
import com.example.bolaksepak.adapter.MatchAdapter;
import com.example.bolaksepak.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchListFragment extends Fragment {



    RecyclerView mMatchListView;
    ArrayList<Match> mMatchList;
    MatchAdapter mMatchAdapter;
    private Context mContext;
    private static final String MATCH_LIST_FLAG = "MATCH_LIST";


    public MatchListFragment() {
        // Required empty public constructor
    }

    public static MatchListFragment newInstance(ArrayList<Match> match) {
        MatchListFragment fragment = new MatchListFragment();
        Bundle args = new Bundle();
        args.putSerializable(MATCH_LIST_FLAG, match);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mMatchList = (ArrayList<Match>) args.getSerializable(MATCH_LIST_FLAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_match_list, container, false);
        mMatchListView = (RecyclerView) rootView.findViewById(R.id.match_list_fragment);
        this.mMatchAdapter = new MatchAdapter(getContext(), mMatchList,null);
        mMatchListView.setAdapter(this.mMatchAdapter);
        mMatchListView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }


}
