package com.example.bolaksepak.homepage;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Match  implements Serializable { //TODO: Kelas Sementara buat bikin recyclerView
    String date; //TODO: Ganti pake tipe data tanggal
    String home_name;
    String away_name;
    int home_score;
    int away_score;
    int home_shots;
    int away_shots;



    public Match(String d, String hn, int hs, String an, int as) {
        date = d;
        home_score = hs;
        home_name = hn;
        away_score = as;
        away_name = an;
    }
}
