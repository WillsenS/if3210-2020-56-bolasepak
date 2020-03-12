package com.example.bolaksepak;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchHolder> {
    Context ctx;
    ArrayList<Match> MatchList;
    int[] images;

    public MatchAdapter(Context ctx, ArrayList<Match> MatchList, int[] images) {
        this.ctx = ctx;
        this.MatchList = MatchList;
        this.images = images;
    }


    @NonNull
    @Override
    public MatchAdapter.MatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.ctx);
        View MatchView = inflater.inflate(R.layout.match, parent, false);
        return new MatchHolder(MatchView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MatchAdapter.MatchHolder holder, int position) {
        holder.match_date.setText(MatchList.get(position).date);
        holder.team1name.setText(MatchList.get(position).home_name);
        holder.away_name.setText(MatchList.get(position).away_name);
        if (MatchList.get(position).home_score == -1 || MatchList.get(position).away_score == -1) {
            holder.home_score.setText("-");
            holder.away_score.setText("-");
        } else {
            holder.home_score.setText(String.valueOf(MatchList.get(position).home_score));
            holder.away_score.setText(String.valueOf(MatchList.get(position).away_score));
        }
//        holder.home_logo.setImageResource(images[0]);
//        holder.away_logo.setImageResource(images[1]);
    }

    @Override
    public int getItemCount() {
        return MatchList.size();
    }

    static class MatchHolder extends RecyclerView.ViewHolder {
        TextView team1name;
        TextView away_name;
        TextView home_score;
        TextView away_score;
        TextView match_date;
        ImageView home_logo;
        ImageView away_logo;

        MatchHolder(@NonNull View itemView) {
            super(itemView);
            team1name = (TextView) itemView.findViewById(R.id.home_club_name);
            away_name = (TextView) itemView.findViewById(R.id.away_club_name);
            home_score = (TextView) itemView.findViewById(R.id.home_club_score);
            away_score = (TextView) itemView.findViewById(R.id.away_club_score);
            match_date = (TextView) itemView.findViewById(R.id.match_date);
//            home_logo = (ImageView) itemView.findViewById(R.id.home_club_logo);
//            away_logo = (ImageView) itemView.findViewById(R.id.away_club_logo);

        }
    }
}
