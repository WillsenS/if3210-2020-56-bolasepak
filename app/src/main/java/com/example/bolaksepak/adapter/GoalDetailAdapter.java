package com.example.bolaksepak.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolaksepak.Match;
import com.example.bolaksepak.R;

import java.util.ArrayList;

public class GoalDetailAdapter extends RecyclerView.Adapter<GoalDetailAdapter.GoalDetailHolder> {
    private ArrayList<String> mGoalList;
    private Context mContext;

    public GoalDetailAdapter(Context ctx, ArrayList<String> goalList) {
        this.mContext = ctx;
        this.mGoalList = goalList;
    }

    @NonNull
    @Override
    public GoalDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        View GoalView = inflater.inflate(R.layout.item_goal_detail , parent, false);

        return new GoalDetailHolder(GoalView);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalDetailHolder holder, int position) {
//        Log.d("Goals: ", "onBindViewHolder: " + mGoalList.toString());
        Log.d("Goalee", "onBindViewHolder: " + mGoalList.get(position));
        holder.goalItem.setText((CharSequence) mGoalList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGoalList.size();
    }

    static class GoalDetailHolder extends RecyclerView.ViewHolder {

        TextView goalItem ;

        GoalDetailHolder(@NonNull View itemView) {
            super(itemView);
            goalItem = itemView.findViewById(R.id.goal_item_text);
        }
    }
}
