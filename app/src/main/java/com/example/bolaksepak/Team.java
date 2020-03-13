package com.example.bolaksepak;

import java.io.Serializable;

public class Team implements Serializable {
    private static final int HOME = 1;
    private static final int AWAY = 2;
    public String name;
    public int score;
    public int shots;
    public String logo_url;
    public String id;
    public String[] goalDetails;

    public Team(Match m, int type) {
        if (type == HOME) {
            name = m.home_name;
            score = m.home_score;
            shots = m.home_shots;
            logo_url = m.home_logo_url;
            goalDetails = m.homeGoalDetails;
            id = m.home_id;
        } else if (type == AWAY) {
            name = m.away_name;
            score = m.away_score;
            shots = m.away_shots;
            logo_url = m.away_logo_url;
            goalDetails = m.awayGoalDetails;
            id = m.away_id;
        }
    }

    public Team() {
        name = "";
        score = 0;
        shots = 0;
        logo_url = "";
        id = "";
    }

    public Team(Team t) {
        score = t.score;
        shots = t.shots;
        logo_url = t.logo_url;
        id = t.id;
        if (t.goalDetails != null) {
            goalDetails = new String[t.goalDetails.length];
            System.arraycopy(t.goalDetails, 0, goalDetails, 0, goalDetails.length);
        }
    }
}
