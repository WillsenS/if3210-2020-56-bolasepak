package com.example.bolaksepak;

import androidx.annotation.NonNull;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Match implements Serializable {
    public String date; //TODO: Ganti pake tipe data tanggal
    public String home_name;
    public String away_name;
    public int home_score;
    public int away_score;
    public int home_shots;
    public int away_shots;
    public String home_logo_url;
    public String away_logo_url;
    public String home_id;
    public String away_id;

    public Match() {
          date = "";
          home_name = "";
          away_name = "";
          home_score = 0;
          away_score = 0;
          home_shots = 0;
          away_shots = 0;
          home_logo_url = "";
          away_logo_url = "";
          home_id = "";
          away_id = "";
    }

    public Match(String d, String hn, int hs, String an, int as) {
        date = d;
        home_score = hs;
        home_name = hn;
        away_score = as;
        away_name = an;
    }

    @NonNull
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                ToStringStyle.MULTI_LINE_STYLE, true, true);
    }
}
