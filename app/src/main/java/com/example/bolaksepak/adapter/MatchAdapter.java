package com.example.bolaksepak.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolaksepak.Match;
import com.example.bolaksepak.R;
import com.example.bolaksepak.ui.homepage.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchHolder> implements Filterable {
    private Context mContext;
    private ArrayList<Match> mMatchList; // Original list
    private ArrayList<Match> mDisplayedMatchList; //list to be display
    private int[] mImages;
    private OnMatchListener mOnMatchListener;

    public MatchAdapter(Context ctx, ArrayList<Match> matchList, int[] images, OnMatchListener onMatchListener) {
        this.mContext = ctx;
        this.mMatchList = matchList;
        this.mDisplayedMatchList = matchList;
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
        holder.match_date.setText(mDisplayedMatchList.get(position).date);
        holder.home_name.setText(mDisplayedMatchList.get(position).home_name);
        holder.away_name.setText(mDisplayedMatchList.get(position).away_name);
        if (mDisplayedMatchList.get(position).home_score == -1 || mDisplayedMatchList.get(position).away_score == -1) {
            holder.home_score.setText("-");
            holder.away_score.setText("-");
        } else {
            holder.home_score.setText(String.valueOf(mDisplayedMatchList.get(position).home_score));
            holder.away_score.setText(String.valueOf(mDisplayedMatchList.get(position).away_score));
        }

        if (! mDisplayedMatchList.get(position).home_logo_url.equals("")) {
            Picasso.get().load(mDisplayedMatchList.get(position).home_logo_url).
                    placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.home_logo);
        } else {
            Picasso.get().load(R.drawable.placeholder)
                    .into(holder.home_logo);
        }

        if (! mDisplayedMatchList.get(position).away_logo_url.equals("")) {
            Picasso.get().load(mDisplayedMatchList.get(position).away_logo_url).
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
        return mDisplayedMatchList.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter(){

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDisplayedMatchList = (ArrayList<Match>) filterResults.values;//has the filtered values
                notifyDataSetChanged();// notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //Toast.makeText(mContext, "filtering performed", Toast.LENGTH_SHORT).show();
                FilterResults results = new FilterResults();
                ArrayList<Match> filteredArrList = new ArrayList<Match>();
                if (mMatchList == null) {
                    Toast.makeText(mContext, "mMatchList null", Toast.LENGTH_SHORT).show();
                    mMatchList = new ArrayList<Match>(mDisplayedMatchList); // saves the original data in mOriginalValues
                }

                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = mMatchList.size();
                    results.values = mMatchList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i=0; i <mMatchList.size(); i++){
                        String home_name = mMatchList.get(i).home_name;
                        String away_name = mMatchList.get(i).away_name;
                        //TODO : implement dengan regex bukan hanya startWith
                        if(home_name.toLowerCase().contains(constraint.toString()) || away_name.toLowerCase().startsWith(constraint.toString())){
                            filteredArrList.add(new Match(mMatchList.get(i)));
                        }
                    }
                    // set the Filtered result to return
                    results.count = filteredArrList.size();
                    results.values = filteredArrList;
                }
                return results;
            }
        };
        return filter;
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
