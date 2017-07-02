package com.example.heman.projectv02;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.heman.projectv02.SurveyComponents.Survey;

import com.example.heman.projectv02.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class SurveySync {

    private Context mContext;

    public SurveySync(Context mContext) {
        this.mContext = mContext;
    }

    /*
     * Function to get the Latest list of Surveys Available according to User's prescribed Language
     */
    public void checkSurveyUpdates() {
        final ArrayList<Survey> fSurvey;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SURVEY_UPDATES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //TODO: DECIDE FORMATE AND PROCEED ACCORDINGLY
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Survey>>() {
                        }.getType();
                        ArrayList<Survey> jsonSurvey = gson.fromJson(response, type);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error in Connection", error.getMessage() + "\n" + error.getLocalizedMessage());
                    }
                });

        RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    /*
     * Used to get Questions for New/Updated Surveys
     */
    public void getSurveyQuestions() {

    }

    /*
     * Function to send Survey response whenever Internet is available
     */
    public void sendSurveyResponse() {

    }

    /*
     * Used to Check if user Login Credentials
     */
    public boolean userLogin() {

        return false;
    }

}
