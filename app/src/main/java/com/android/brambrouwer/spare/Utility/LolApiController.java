package com.android.brambrouwer.spare.Utility;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;


public class LolApiController {

    public static String allChampsUrl = "https://global.api.pvp.net/api/lol/static-data/euw/v1.2/champion?champData=info&api_key=6cbf8df1-8340-4de9-bf1d-f1034b3b7171";
    private ProgressDialog progressDialog;


    public void get(String url, Context context, final VolleyCallback callback) {

        showDialog(context); //Show loading dialog

        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    callback.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                callback.onError(volleyError);
            }
        }) ;
        MySingleton.getInstance(context).addToRequestQueue(sr);
    }

    // UTILITY
    private void showDialog(Context context) {
        progressDialog = new ProgressDialog(context,
                ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    //Generates an error message corresponding with error response
    public void generateErrorMessage(Context context, VolleyError error) {
        String errorMessage = "Something went wrong. No idea what went wrong tho.";
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            errorMessage ="Connection timed out, please check your connection and try again.";
        } else if (error instanceof AuthFailureError) {
            errorMessage = "There was an authentication failure while performing the request.";
        } else if (error instanceof ServerError) {
            errorMessage = "The server responded with an error response.\n Probably caused by invalid request parameters (invalid id/name/genre).";
        } else if (error instanceof NetworkError) {
            errorMessage = "Network encountered while performing request.";
        } else if (error instanceof ParseError) {
            errorMessage = "Server response could not be parsed.";
        }
        showErrorDialog(context,errorMessage);
    }

    //Show error dialog to user with relevant message
    public void showErrorDialog(Context context, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    //Used so we can access return values directly in the activity that calls for them
    public interface VolleyCallback {
        void onSuccess(String result) throws JSONException;
        void onError(VolleyError errorMessage);
    }



}
