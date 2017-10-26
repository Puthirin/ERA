package com.example.puthirin.era;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by imac-13 on 10/26/17.
 */

public class AppSingleton {
    private static AppSingleton mAppSingletonInstance;
    private RequestQueue mRequestQueue;
    private static Context mcontext;

    private AppSingleton(Context context){
        mcontext = context;
        mRequestQueue = getRequestQueue();
    }
    public static synchronized AppSingleton getInstance(Context context){
        if (mAppSingletonInstance == null){
            mAppSingletonInstance = new AppSingleton(context);
        }
        return mAppSingletonInstance;
    }
    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mcontext.getApplicationContext());
        }
        return mRequestQueue;
    }
    public void addToRequestQueue(Request req,String tag){
        req.setTag(tag);
        getRequestQueue().add(req);
    }
}
