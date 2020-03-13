package com.example.bolaksepak.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bolaksepak.R;
import com.squareup.picasso.Picasso;

public class MatchTeamDataLoader {

    public MatchTeamDataLoader() {

    }
    public void loadImage(String imageUrl, ImageView imageView) {
        if (!imageUrl.equals("")) {
            Picasso.get().load(imageUrl).
                    placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(imageView);
        } else {
            Picasso.get().load(R.drawable.placeholder)
                    .into(imageView);
        }

    }

    public void validateAndSetNumberData(int homeNumVal, int awayNumVal, TextView homeTextView, TextView awayTextView) {
        if (homeNumVal == -1) {
            homeTextView.setText("-");
        } else {
            homeTextView.setText(String.valueOf(homeNumVal));
        }
        if (awayNumVal == -1) {
            awayTextView.setText("-");
        } else {
            awayTextView.setText(String.valueOf(awayNumVal));
        }

    }
}
