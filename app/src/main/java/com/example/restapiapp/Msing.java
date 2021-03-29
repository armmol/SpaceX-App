package com.example.restapiapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Msing {
    private static Msing msing;
    private RequestQueue requestQueue;
    private static Context context;
    private Msing(Context cont) {
        context = cont;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue () {
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue (context.getApplicationContext ());
        }return requestQueue;
    }

    public static synchronized Msing getInstance(Context context){
        if(msing==null){
            msing=new Msing (context);
        }
        return msing;
    }

    public <T> void addToRequestQueue(Request request){
        requestQueue.add (request);
    }
}
