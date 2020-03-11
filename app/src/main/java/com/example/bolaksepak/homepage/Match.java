package com.example.bolaksepak.homepage;

public class Match { //TODO: Kelas Sementara buat bikin recyclerView
     String date; //TODO: Ganti pake tipe data tanggal
     int home_score;
     String home_name;
     int away_score;
     String away_name;

    public Match(String d, String hn, int hs, String an, int as) {
        date = d;
        home_score = hs;
        home_name = hn;
        away_score = as;
        away_name = an;
    }
}
