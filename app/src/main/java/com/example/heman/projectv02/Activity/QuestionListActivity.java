package com.example.heman.projectv02.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.heman.projectv02.SurveyComponents.Question;
import com.example.heman.projectv02.Adapters.QuestionAdapter;
import com.example.heman.projectv02.R;
import com.example.heman.projectv02.SqliteDb.SurveyDbHandler;
import com.example.heman.projectv02.SurveyComponents.Survey;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class QuestionListActivity extends AppCompatActivity {

    private static QuestionAdapter adapter;
    ListView listView;
    private ArrayList<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        listView= (ListView) findViewById(R.id.qlQuestionList);
        questionList = new ArrayList<>();
        SurveyDbHandler surveyDbHandler = new SurveyDbHandler(this);
        surveyDbHandler.setUpDb();
        for(int i=0;i<10;i++){
            Question question=new Question();
            question.setLanguage("en");
            question.setqText("This is Question "+String.valueOf(i));
            question.setqNo(i);
            JSONObject object=new JSONObject();
                for(int j=0;j<3;j++){
                    try {
                        object.put(String.valueOf(j),"Option "+String.valueOf(j)+" of Question "+String.valueOf(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this,"Error in Making Options",Toast.LENGTH_LONG).show();
                    }
                }
            question.setOptions(object);
            question.setMultiSelect(i%2==0);
            question.setsId("1");
            questionList.add(question);

            //TODO: CALL THE BELOW LINE ONLY ONCE AND THEN NEVER AGAIN
            //surveyDbHandler.storeQuestion(question);

        }

        ArrayList<Question> newQuestionList = (ArrayList<Question>) surveyDbHandler.getQuestionsForSurvey("en", "1");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Question>>() {
        }.getType();
        String jsonString = gson.toJson(newQuestionList, type);
        Log.d("Survey JSON String", "STRING::::::\n\n\n" + jsonString);

        ArrayList<Question> jsonQuestion = gson.fromJson(jsonString, type);

        if (jsonQuestion == null) {
            Log.d("JSON SURVEY IS ", "NULL!!!");
        }

        adapter = new QuestionAdapter(this, jsonQuestion, null);
        listView.setAdapter(adapter);
    }

}
