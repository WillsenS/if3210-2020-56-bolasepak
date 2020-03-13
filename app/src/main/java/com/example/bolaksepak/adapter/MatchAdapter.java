package com.example.bolaksepak.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolaksepak.Match;
import com.example.bolaksepak.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchHolder> {
    private Context mContext;
    private ArrayList<Match> mMatchList;
    private int[] mImages;
    private OnMatchListener mOnMatchListener;

    public MatchAdapter(Context ctx, ArrayList<Match> matchList, int[] images, OnMatchListener onMatchListener) {
        this.mContext = ctx;
        this.mMatchList = matchList;
        this.mImages = images;
        this.mOnMatchListener = onMatchListener;
    }


    @NonNull
    @Override
    public MatchAdapter.MatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        View MatchView = inflater.inflate(R.layout.match, parent, false);
        return new MatchHolder(MatchView, mOnMatchListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MatchAdapter.MatchHolder holder, int position) {
        holder.match_date.setText(mMatchList.get(position).date);
        holder.home_name.setText(mMatchList.get(position).home_name);
        holder.away_name.setText(mMatchList.get(position).away_name);
        if (mMatchList.get(position).home_score == -1 || mMatchList.get(position).away_score == -1) {
            holder.home_score.setText("-");
            holder.away_score.setText("-");
        } else {
            holder.home_score.setText(String.valueOf(mMatchList.get(position).home_score));
            holder.away_score.setText(String.valueOf(mMatchList.get(position).away_score));
        }

        if (! mMatchList.get(position).home_logo_url.equals("")) {
            Picasso.get().load(mMatchList.get(position).home_logo_url).
                    placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.home_logo);
        } else {
            Picasso.get().load(R.drawable.placeholder)
                    .into(holder.home_logo);
        }

        if (! mMatchList.get(position).away_logo_url.equals("")) {
            Picasso.get().load(mMatchList.get(position).away_logo_url).
                    placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.away_logo);
        } else {
            Picasso.get().load(R.drawable.placeholder)
                    .into(holder.away_logo);
        }
    }

    @Override
    public int getItemCount() {
        return mMatchList.size();
    }



    static class MatchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView home_name;
        TextView away_name;
        TextView home_score;
        TextView away_score;
        TextView match_date;
        ImageView home_logo;
        ImageView away_logo;
        OnMatchListener onMatchListener;

        MatchHolder(@NonNull View itemView, OnMatchListener onMatchListener) {
            super(itemView);
            home_name = (TextView) itemView.findViewById(R.id.home_club_name);
            away_name = (TextView) itemView.findViewById(R.id.away_club_name);
            home_score = (TextView) itemView.findViewById(R.id.home_club_score);
            away_score = (TextView) itemView.findViewById(R.id.away_club_score);
            match_date = (TextView) itemView.findViewById(R.id.match_date);
            home_logo = (ImageView) itemView.findViewById(R.id.home_club_logo);
            away_logo = (ImageView) itemView.findViewById(R.id.away_club_logo);
            this.onMatchListener = onMatchListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMatchListener.OnMatchClick(getAdapterPosition());
        }
    }
    public interface OnMatchListener {
        void OnMatchClick(int position);
    }


}
