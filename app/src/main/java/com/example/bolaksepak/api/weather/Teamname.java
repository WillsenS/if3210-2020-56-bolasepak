package com.example.bolaksepak.api.weather;

import com.example.bolaksepak.Team;

import java.util.ArrayList;
import java.util.List;

public class Teamname {
    private List<teams> teams = new ArrayList<teams>(100);

    public teams getTeams(int w) {
        return teams.get(w);
    }
    public List<teams> getTeams() {
        return teams;
    }


}
