package com.example.heman.projectv02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionListActivity extends AppCompatActivity {

    private ArrayList<Question> questionList;
    ListView listView;
    private static QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        listView= (ListView) findViewById(R.id.qlQuestionList);
        questionList=new ArrayList<Question>();
        for(int i=0;i<10;i++){
            Question question=new Question();
            question.setLanguage("Langauge"+String.valueOf(i));
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
            question.setsId(String.valueOf(i));
            questionList.add(question);
        }
        adapter=new QuestionAdapter(this,questionList,null);
        listView.setAdapter(adapter);
    }
}
