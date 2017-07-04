package com.example.heman.projectv02.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.heman.projectv02.R;
import com.example.heman.projectv02.SharedPreferences.SharedPrefManager;
import com.example.heman.projectv02.SurveyComponents.Survey;
import com.example.heman.projectv02.Adapters.SurveyAdapter;
import com.example.heman.projectv02.SqliteDb.SurveyDbHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SurveyListActivity extends AppCompatActivity implements SurveyAdapter.SurveyElementOnClickListener {

    ArrayList<Survey> surveyList;
    ListView listView;
    private SurveyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_list);
        SurveyDbHandler surveyDbHandler = new SurveyDbHandler(this);
        surveyDbHandler.setUpDb();
        listView = (ListView) findViewById(R.id.slSurveyList);
        surveyList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Survey survey = new Survey();
            survey.setsId(String.valueOf(i));
            survey.setTitle("Title " + String.valueOf(i));
            survey.setDescription("Desctiption " + String.valueOf(i));
            survey.setTotalQuestions(i);
            survey.setLanguage("en");
            survey.setVersion(i);
            surveyList.add(survey);

            //TODO: CALL THE BELOW LINE ONLY ONCE AND THEN DONT CALL IT
            //Log.d("Survey Returns ",String.valueOf(surveyDbHandler.storeSurvey(survey)));
        }

        ArrayList<Survey> newSurveyList = (ArrayList<Survey>) surveyDbHandler.getSurveyList("en");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Survey>>() {
        }.getType();
        String jsonString = gson.toJson(newSurveyList, type);
        Log.d("Survey JSON String", "STRING::::::\n\n\n" + jsonString);

        ArrayList<Survey> jsonSurvey = gson.fromJson(jsonString, type);

        if (jsonSurvey == null) {
            Log.d("JSON SURVEY IS ", "NULL!!!");
        }

        adapter = new SurveyAdapter(this, jsonSurvey, this);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.FinalPage:
                startActivity(new Intent(this, FinalPage.class));
                break;
        }
        return true;
    }

    @Override
    public void onClick(int position) {
        Survey survey=surveyList.get(position);
        Log.d("SURVEY CLICKED", survey.getTitle());
        Toast.makeText(this, survey.getTitle(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, QuestionListActivity.class);
        intent.putExtra("76gj", "dsf");
        startActivity(intent);
    }
}
