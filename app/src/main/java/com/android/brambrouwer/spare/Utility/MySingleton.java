package com.android.brambrouwer.spare.Utility;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/*
Used to access user info and credentials troughout the app / access requestqueue
 */
public class MySingleton {

    private static MySingleton mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;


    private MySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }



    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


}