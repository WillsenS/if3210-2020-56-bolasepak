package com.example.bolaksepak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolaksepak.R;

public class GoalDetailAdapter extends RecyclerView.Adapter<GoalDetailAdapter.GoalDetailHolder> {
    private String[] mGoalList;
    private Context mContext;

    public GoalDetailAdapter(Context ctx, String[] goalList) {
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
        holder.goalItem.setText(mGoalList[position]);
    }

    @Override
    public int getItemCount() {
        return mGoalList.length;
    }

    static class GoalDetailHolder extends RecyclerView.ViewHolder {

        TextView goalItem ;

        GoalDetailHolder(@NonNull View itemView) {
            super(itemView);
            goalItem = itemView.findViewById(R.id.goal_item_text);
        }
    }
}
