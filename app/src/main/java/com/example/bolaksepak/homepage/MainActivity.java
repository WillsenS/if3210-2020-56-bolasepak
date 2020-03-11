package com.example.bolaksepak.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bolaksepak.Match;
import com.example.bolaksepak.MatchAdapter;
import com.example.bolaksepak.R;
import com.example.bolaksepak.eventdetail.EventDetailActivity;
import com.example.bolaksepak.lastEvent;
import com.example.bolaksepak.nextEvent;
import com.example.bolaksepak.searchById;
import com.example.bolaksepak.searchByName;
import com.example.bolaksepak.theSportDB;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private theSportDB theSportDB;

    RecyclerView MatchListView;
    Match[] MatchList = new Match[10];
    int[] clubImages = {
            R.drawable.club1,
            R.drawable.club2
    };
    MatchAdapter md;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        MatchListView = (RecyclerView) findViewById(R.id.matchlist);
        for (int i = 0; i < MatchList.length; i++) {
            MatchList[i] = new Match("99 Maret 2020", "Club 1", 1, "Club 2", 2);
        }

        this.md = new MatchAdapter(this, MatchList, clubImages);
        MatchListView.setAdapter(md);
        MatchListView.setLayoutManager(new LinearLayoutManager(this));


        //This part is for API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/api/v1/json/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        theSportDB = retrofit.create(com.example.bolaksepak.theSportDB.class);

        //getlastevent();
        //getnextevent();
        //getsearchbyid();
        //getsearchbyname();
    }

    private void getsearchbyname() {
        Call<List<searchByName>> call = theSportDB.getsearchByName("3"); //TO DO: GANTI param

        call.enqueue(new Callback<List<searchByName>>() {
            @Override
            public void onResponse(Call<List<searchByName>> call, Response<List<searchByName>> response) {

            }

            @Override
            public void onFailure(Call<List<searchByName>> call, Throwable t) {

            }
        });

        //TO DO : String Concat, Assign yang mana yang perlu diubah
    }

    private void getsearchbyid() {
        Call<List<searchById>> call = theSportDB.getsearchById(3); //TO DO: GANTI param

        call.enqueue(new Callback<List<searchById>>() {
                         @Override
                         public void onResponse(Call<List<searchById>> call, Response<List<searchById>> response) {

                         }

                         @Override
                         public void onFailure(Call<List<searchById>> call, Throwable t) {
                         }
                     });


                //TO DO : String Concat, Assign yang mana yang perlu diubah
    }

    private void getnextevent() {
        Call<List<nextEvent>> call = theSportDB.getNextEvent(3); //TO DO: GANTI param

        call.enqueue(new Callback<List<nextEvent>>() {
                         @Override
                         public void onResponse(Call<List<nextEvent>> call, Response<List<nextEvent>> response) {

                         }

                         @Override
                         public void onFailure(Call<List<nextEvent>> call, Throwable t) {

                         }
        });


                //TO DO : String Concat, Assign yang mana yang perlu diubah
    }

    private void getlastevent() {
        Call<List<lastEvent>> call = theSportDB.getLastEvent(3); //TO DO: GANTI param

        call.enqueue(new Callback<List<lastEvent>>() {
            @Override
            public void onResponse(Call<List<lastEvent>> call, Response<List<lastEvent>> response) {
                if (!response.isSuccessful()) {
                    //setText("CODE: " + response.code());
                    //return;
                }

                //List<lastEvent> lastevent = response.body();
            }

            @Override
            public void onFailure(Call<List<lastEvent>> call, Throwable t) {
                //setText("t.getMessage());
            }
        });

        //TO DO : String Concat, Assign yang mana yang perlu diubah
    }

    public void viewMatchDetail(View view) {
        Intent intent = new Intent(this, EventDetailActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
