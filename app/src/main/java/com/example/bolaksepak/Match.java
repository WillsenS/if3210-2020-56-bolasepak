package com.example.bolaksepak;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Match  implements Serializable { //TODO: Kelas Sementara buat bikin recyclerView
    public String date; //TODO: Ganti pake tipe data tanggal
    public String home_name;
    public String away_name;
    public int home_score;
    public int away_score;
    public int home_shots;
    public int away_shots;



    public Match(String d, String hn, int hs, String an, int as) {
        date = d;
        home_score = hs;
        home_name = hn;
        away_score = as;
        away_name = an;
    }
}
