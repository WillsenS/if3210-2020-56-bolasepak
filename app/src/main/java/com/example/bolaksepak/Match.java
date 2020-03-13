package com.example.bolaksepak;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

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
    public ArrayList<String> homeGoalDetails;
    public ArrayList<String> awayGoalDetails;


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

    public Match(Match m) {
        date = m.date;
        home_name = m.home_name;
        away_name = m.away_name;
        home_score = m.home_score;
        away_score = m.away_score;
        home_shots = m.home_shots;
        away_shots = m.away_shots;
        home_logo_url = m.home_logo_url;
        away_logo_url = m.away_logo_url;
        home_id = m.home_id;
        away_id = m.away_id;
        homeGoalDetails = new ArrayList<>(m.homeGoalDetails);
        awayGoalDetails = new ArrayList<>(m.awayGoalDetails);
    }

    @NonNull
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                ToStringStyle.MULTI_LINE_STYLE, true, true);
    }
}
