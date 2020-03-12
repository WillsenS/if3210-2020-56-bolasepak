package com.example.bolaksepak.api.matchschedule;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class MatchFetcherSingleton { //singleton class to fetch data for Homepage match list
    public static final String mBaseUrl = "https://www.thesportsdb.com/api/v1/json/1/";
    private static MatchFetcherSingleton mScheduleFetcher = null;
    private static MatchFetcherSingleton mInstance;
    private static Context mContext;
    public RequestQueue mVolleyRequestQueue;

    private MatchFetcherSingleton(Context ctx) {
        mContext = ctx;
        mVolleyRequestQueue = getRequestQueue();
    }

    public static synchronized MatchFetcherSingleton getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new MatchFetcherSingleton(ctx);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mVolleyRequestQueue == null) {
            mVolleyRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mVolleyRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
